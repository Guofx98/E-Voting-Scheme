package com.example.caserver.init;

import com.alibaba.fastjson.JSONObject;
import com.example.caserver.entity.Community;
import com.example.caserver.entity.Key;
import com.example.caserver.entity.PublicParam;
import com.example.caserver.entity.Result;
import com.example.caserver.utils.BlindSignature;
import org.hyperledger.fabric.gateway.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.Map;

@Component
public class Init implements CommandLineRunner {

    @Autowired
    Contract contract;

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void run(String... args) throws Exception {
        List<Community> communities = mongoTemplate.find(new Query(), Community.class, "Communities");
        for(Community community : communities){
            String res = new String(contract.evaluateTransaction("getSignPublicKey",community.getId()));
            Result result = JSONObject.parseObject(res,Result.class);
            if(result.getCode() != 200){
                Map<String, Object> map = BlindSignature.initKey();
                RSAPublicKey publicKey = (RSAPublicKey) map.get("publicKey");
                RSAPrivateKey privateKey = (RSAPrivateKey) map.get("privateKey");
                Key pk = new Key();
                Key sk = new Key();
                sk.setD(privateKey.getPrivateExponent().toString());
                sk.setN(privateKey.getModulus().toString());
                sk.setBASE64("-----BEGIN PRIVATE KEY-----\n"+BlindSignature.encryptBASE64(privateKey.getEncoded())+"-----END PRIVATE KEY-----");
                sk.setName("BlindSignPrivateKey_"+community.getId());
                sk.setCommunityId(community.getId());
                pk.setE(publicKey.getPublicExponent().toString());
                pk.setN(publicKey.getModulus().toString());
                pk.setBASE64("-----BEGIN PUBLIC KEY-----\n"+BlindSignature.encryptBASE64(publicKey.getEncoded())+"-----END PUBLIC KEY-----");
                pk.setName("BlindSignPublicKey_"+community.getId());
                pk.setCommunityId(community.getId());
                mongoTemplate.insert(pk);
                mongoTemplate.insert(sk);
                String ss = new String(contract.submitTransaction("setSignPublicKey", JSONObject.toJSONString(pk),community.getId()));
            }
        }
        String res = new String(contract.evaluateTransaction("getPublicParam"));
        Result result = JSONObject.parseObject(res,Result.class);
        if(result.getCode() != 200){
            System.out.println("系统初始化中，生产公共参数... ...");
            BigInteger one = new BigInteger("1");
            BigInteger two = new BigInteger("2");
            BigInteger q, qp, p, a, g;
            int certainty = 1000;
            SecureRandom sr = new SecureRandom();
            // blq长度的q， q是p-1的素因子
            //生成BigInteger伪随机数，它可能是（概率不小于1 - 1/2certainty）一个具有指定 bitLength 的素数
            q = new BigInteger(1024, certainty, sr);
            qp = BigInteger.ONE;
            do { // 选择一个素数 p
                p = q.multiply(qp).multiply(two).add(one);
                if(p.isProbablePrime(certainty))
                    break;
                qp = qp.add(BigInteger.ONE);
            } while(true);

            while(true) {
                a = (two.add(new BigInteger(1024, 100, sr))).mod(p);// (2+x) mod p
                BigInteger ga = (p.subtract(BigInteger.ONE)).divide(q);// (p-1)/q
                g = a.modPow(ga, p); // a^ga mod p = 1
                if(g.compareTo(BigInteger.ONE) != 0) // g!=1
                    break;
            }
            PublicParam publicParam = new PublicParam();
            publicParam.setG(g.toString());
            publicParam.setP(p.toString());
            publicParam.setQ(q.toString());
            String rs = new String(contract.submitTransaction("setPublicParam", JSONObject.toJSONString(publicParam)));
        }

    }
}
