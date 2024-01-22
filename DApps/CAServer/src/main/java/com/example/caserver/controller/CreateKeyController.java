package com.example.caserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.caserver.entity.Key;
import com.example.caserver.entity.Result;
import com.example.caserver.entity.VoteKey;
import com.example.caserver.utils.BlindSignature;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.hyperledger.fabric.gateway.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

@RestController
public class CreateKeyController {
    @Autowired
    Contract contract;

    @Autowired
    MongoTemplate mongoTemplate;

    static{
        try{
            Security.addProvider(new BouncyCastleProvider());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @GetMapping("/createVoteKey")
    public Result createVoteKey(@RequestParam(value = "proposalId") String proposalId) throws Exception {

        //生成密钥对
        VoteKey votekey = mongoTemplate.findById("VotePublicKey_" + proposalId, VoteKey.class);
        if (votekey!=null)
            return Result.error("密钥已存在！");
        Map<String, Object> map = BlindSignature.initKey();
        RSAPublicKey publicKey = (RSAPublicKey) map.get("publicKey");
        RSAPrivateKey privateKey = (RSAPrivateKey) map.get("privateKey");
        VoteKey pk = new VoteKey();
        VoteKey sk = new VoteKey();
        sk.setD(privateKey.getPrivateExponent().toString());
        sk.setN(privateKey.getModulus().toString());
        sk.setBASE64("-----BEGIN PRIVATE KEY-----\n"+BlindSignature.encryptBASE64(privateKey.getEncoded())+"-----END PRIVATE KEY-----");
        sk.setName("VotePrivateKey_"+proposalId);
        sk.setProposalId(proposalId);
        pk.setE(publicKey.getPublicExponent().toString());
        pk.setN(publicKey.getModulus().toString());
        pk.setBASE64("-----BEGIN PUBLIC KEY-----\n"+BlindSignature.encryptBASE64(publicKey.getEncoded())+"-----END PUBLIC KEY-----");
        pk.setName("VotePublicKey_"+proposalId);
        pk.setProposalId(proposalId);
        mongoTemplate.save(pk);
        mongoTemplate.save(sk);
        String res = new String(contract.submitTransaction("setVotePublicKey",proposalId,JSONObject.toJSONString(pk)));
        Result result = JSONObject.parseObject(res,Result.class);
        if(result.getCode()!=200)
            return Result.error("设置失败！");
        return Result.success(null);
    }
}
