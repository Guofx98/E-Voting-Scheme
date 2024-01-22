package E_Voting;

import E_Voting.Model.*;
import E_Voting.lang.Result;
import com.owlike.genson.Genson;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.*;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Contract(
        name = "E-Voting",
        info = @Info(title = "E-Voting contract",
                version = "0.0.1",
                license = @License(
                        name = "Apache 2.0 Licence",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"))
)
@Default
public class MainCode implements ContractInterface {

    private final Genson genson = new Genson();

    @Transaction()
    public void initLedger(final Context ctx){

    }

    @Transaction()
    public String createVote(final Context ctx, String id ,String proposal){
        ChaincodeStub stub = ctx.getStub();
        stub.putStringState("PROPOSAL_"+id,proposal);
        return genson.serialize(Result.success(null));
    }

    @Transaction()
    public String queryProposal(final Context ctx,String communityId){
        ChaincodeStub stub = ctx.getStub();
        String queryString = "{\"selector\":{\"docType\":\"proposal\",\"communityId\":\""+communityId+"\"}, \"use_index\":[\"_design/proposalIndexDoc1\", \"proposalIndex1\"]}";
        QueryResultsIterator<KeyValue> resultsIterator = stub.getQueryResult(queryString);
        List<String> res1 = new ArrayList<>();
        for(KeyValue result: resultsIterator){
            res1.add(result.getStringValue());
        }
        return genson.serialize(Result.success(res1));
    }

    @Transaction()
    public String queryProposalNoResult(final Context ctx){
        ChaincodeStub stub = ctx.getStub();
        String queryString = "{\"selector\":{\"docType\":\"proposal\",\"hasResult\": false }, \"use_index\":[\"_design/proposalIndexDoc2\", \"proposalIndex2\"]}";
        QueryResultsIterator<KeyValue> resultsIterator = stub.getQueryResult(queryString);
        List<String> res1 = new ArrayList<>();
        for(KeyValue result: resultsIterator){
            res1.add(result.getStringValue());
        }
        return genson.serialize(Result.success(res1));
    }

    @Transaction()
    public String setSignPublicKey(final Context ctx,String pk, String communityId){
        ChaincodeStub stub = ctx.getStub();
        if(pk==null||pk.equals(""))
            return genson.serialize(Result.error("公钥为空！"));
        stub.putStringState("BlindSignPublicKey_"+communityId,pk);
        return genson.serialize(Result.success(null));
    }

    @Transaction()
    public String getSignPublicKey(final Context ctx, String communityId){
        ChaincodeStub stub = ctx.getStub();
        String blindSignPublicKey = stub.getStringState("BlindSignPublicKey_"+communityId);
        if(blindSignPublicKey.isEmpty()){
            return genson.serialize(Result.error("无法找到盲签名公钥！"));
        }
        return genson.serialize(Result.success(blindSignPublicKey));
    }

    @Transaction()
    public String setVotePublicKey(final Context ctx, String proposalId, String pk){
        ChaincodeStub stub = ctx.getStub();
        if(pk.isEmpty()||proposalId.isEmpty())
            return genson.serialize(Result.error("公钥为空！"));
        stub.putStringState("VotePublicKey_"+proposalId,pk);
        return genson.serialize(Result.success(null));
    }

    @Transaction()
    public String getVotePublicKey(final Context ctx,String proposalId){
        ChaincodeStub stub = ctx.getStub();
        String votePublicKey = stub.getStringState("VotePublicKey_"+proposalId);
        if(votePublicKey.isEmpty()){
            return genson.serialize(Result.error("无法找到该投票公钥！"));
        }
        return genson.serialize(Result.success(votePublicKey));
    }

    @Transaction()
    public String setPublicParam(final Context ctx, String publicParam){
        ChaincodeStub stub = ctx.getStub();
        if(publicParam.isEmpty())
            return genson.serialize(Result.error("公共参数为空！"));
        stub.putStringState("PublicParam",publicParam);
        return genson.serialize(Result.success(null));
    }

    @Transaction()
    public String getPublicParam(final Context ctx){
        ChaincodeStub stub = ctx.getStub();
        String publicParam = stub.getStringState("PublicParam");
        if(publicParam.isEmpty()){
            return genson.serialize(Result.error("无法找到公共参数！"));
        }
        return genson.serialize(Result.success(publicParam));
    }

    @Transaction()
    public String setVoteToken(final Context ctx, String token, String blindSign, String communityId) throws NoSuchAlgorithmException {
        ChaincodeStub stub = ctx.getStub();
        String blindSignPublicKey = stub.getStringState("BlindSignPublicKey_"+communityId);
        Key pk = genson.deserialize(blindSignPublicKey, Key.class);
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(token.getBytes());
        BigInteger msgHash = new BigInteger(1,md.digest());
        BigInteger E = new BigInteger(pk.getE());
        BigInteger N = new BigInteger(pk.getN());
        boolean res = msgHash.equals(new BigInteger(blindSign).modPow(E, N));
        if(!res)
            return genson.serialize(Result.error("验签失败！"));
        VoteToken voteToken = new VoteToken();
        voteToken.setToken(token);
        voteToken.setCommunityId(communityId);
        stub.putStringState("VoteToken_"+token,genson.serialize(voteToken));
        return genson.serialize(Result.success(null));
    }

    @Transaction()
    public String getProposalById(final Context ctx, String proposalId){
        ChaincodeStub stub = ctx.getStub();
        String stringState = stub.getStringState("PROPOSAL_" + proposalId);
        if (stringState==null)
            return genson.serialize(Result.error("未找到该议题！"));
        return genson.serialize(Result.success(stringState));
    }

    @Transaction()
    public String vote(final Context ctx, String PK, String B, String C, String R, String proposalId, String content) throws NoSuchAlgorithmException {
        ChaincodeStub stub = ctx.getStub();
        String stringState = stub.getStringState("VoteToken_"+PK);
        if(stringState==null)
            return genson.serialize(Result.error("未找到该账号！"));
        VoteToken voteToken = genson.deserialize(stringState, VoteToken.class);
        String proposalStr = stub.getStringState("PROPOSAL_" + proposalId);
        if (proposalStr == null)
            return genson.serialize(Result.error("未找到该议题！"));
        Proposal proposal = genson.deserialize(proposalStr, Proposal.class);
        if(!proposal.getCommunityId().equals(voteToken.getCommunityId()))
            return genson.serialize(Result.error("您不是该小区业主，无权投票！"));
        LocalDateTime dateTime = LocalDateTime.parse(proposal.getDeadline());
        LocalDateTime now = LocalDateTime.now().plusHours(8);
        boolean r = dateTime.isAfter(now);
        if(!r)
            return genson.serialize(Result.error("该投票已过期！"));

        String queryString = "{\"selector\":{\"docType\":\"vote\",\"proposalId\":\""+proposalId+"\",\"pk\":\""
                +PK+"\"}, \"use_index\":[\"_design/voteIndexDoc1\", \"voteIndex1\"]}";
        QueryResultsIterator<KeyValue> resultsIterator = stub.getQueryResult(queryString);
        int count = 0;
        for (KeyValue result: resultsIterator){
            if (result!=null)
                count++;
        }
        if (count > 0)
            return genson.serialize(Result.error("不能重复投票!"));

        String str = PK+B;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(str.getBytes());
        BigInteger msgHash = new BigInteger(1,md.digest());
        if(!msgHash.toString().equals(C)){
            return genson.serialize(Result.error("验证错误！"));
        }
        String publicParamStr = stub.getStringState("PublicParam");
        PublicParam publicParam = genson.deserialize(publicParamStr, PublicParam.class);
        BigInteger g = new BigInteger(publicParam.getG());
        BigInteger p = new BigInteger(publicParam.getP());
        BigInteger pk = new BigInteger(PK);
        BigInteger x1 = g.modPow(new BigInteger(R),p);
        BigInteger x2 = pk.modPow(msgHash,p).multiply(new BigInteger(B).mod(p));
        if(x1.equals(x2))
            return genson.serialize(Result.success("验证失败！"));
        Vote vote = new Vote();
        vote.setVoteId(UUID.randomUUID().toString());
        vote.setVoteContent(content);
        vote.setDocType("vote");
        vote.setPk(PK);
        vote.setProposalId(proposalId);
        stub.putStringState("VOTE_"+vote.getVoteId(),genson.serialize(vote));
        return genson.serialize(Result.success("投票成功！"));
    }

    @Transaction()
    public String count(final Context ctx, String proposalId, String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        try {
            ChaincodeStub stub = ctx.getStub();
            //校验
            String stringState = stub.getStringState("PROPOSAL_" + proposalId);
            if (stringState == null)
                return genson.serialize(Result.error("未找到该议案！"));
            Proposal proposal = genson.deserialize(stringState, Proposal.class);
            LocalDateTime dateTime = LocalDateTime.parse(proposal.getDeadline());
            LocalDateTime now = LocalDateTime.now().plusHours(8);
            if (dateTime.isAfter(now))
                return genson.serialize(Result.error("该议案正在投票中..."));
            //创建计票结果对象
            VoteResult voteResult = new VoteResult();
            //获取选票
            String queryString = "{\"selector\":{\"docType\":\"vote\",\"proposalId\":\"" + proposalId + "\"}, \"use_index\":[\"_design/voteIndexDoc2\", \"voteIndex2\"]}";
            QueryResultsIterator<KeyValue> resultsIterator = stub.getQueryResult(queryString);
            int totalVotes = 0;
            int validatedVotes = 0;
            HashMap<String, Integer> count = new HashMap<>();

            for (KeyValue result : resultsIterator) {
                Vote vote = genson.deserialize(result.getStringValue(), Vote.class);
                //解密选票
                byte[] content = Base64.getDecoder().decode(vote.getVoteContent());
                //base64编码的私钥
                byte[] decoded = Base64.getMimeDecoder().decode(privateKey.substring(28, privateKey.length() - 26));
                RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
                //RSA解密
                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.DECRYPT_MODE, priKey);
                int inputLen = content.length;
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                int offset = 0;
                byte[] cache;
                int i = 0;
                // 对数据分段解密
                while (inputLen - offset > 0) {
                    if (inputLen - offset > 128) {
                        cache = cipher.doFinal(content, offset, 128);
                    } else {
                        cache = cipher.doFinal(content, offset, inputLen - offset);
                    }
                    out.write(cache, 0, cache.length);
                    i++;
                    offset = i * 128;
                }
                byte[] decryptedData = out.toByteArray();
                out.close();
                // 解密后的内容
                String v = new String(decryptedData, StandardCharsets.UTF_8);
                //选票校验
                if (v.indexOf("VOTE_") == 0) {
                    validatedVotes += 1;
                    String res = v.substring(5, v.lastIndexOf('_'));
                    if(count.get(res)==null)
                        count.put(res, 1);
                    else
                        count.put(res, count.get(res) + 1);
                }
                totalVotes += 1;
            }
            voteResult.setValidatedVotes(validatedVotes);
            voteResult.setTotalVotes(totalVotes);
            voteResult.setCount(count);
            voteResult.setProposalId(proposalId);
            //发布计票结果，公布私钥
            proposal.setHasResult(true);
            stub.putStringState("PROPOSAL_" + proposalId, genson.serialize(proposal));
            stub.putStringState("VoteResult_" + proposalId, genson.serialize(voteResult));
            Key key = new Key();
            key.setName("VotePrivateKey_" + proposalId);
            key.setBASE64(privateKey);
            stub.putStringState("VotePrivateKey_" + proposalId, genson.serialize(privateKey));
            return genson.serialize(Result.success(null));
        }
        catch (Exception e){
            return genson.serialize(Result.error(e.toString()));
        }
    }

    @Transaction()
    public String getCountResult(final Context ctx, String proposalId){
        ChaincodeStub stub = ctx.getStub();
        String stringState = stub.getStringState("VoteResult_" + proposalId);
        if(stringState==null)
            return genson.serialize(Result.error("没有找到结果！"));
        return genson.serialize(Result.success(stringState));
    }
}

