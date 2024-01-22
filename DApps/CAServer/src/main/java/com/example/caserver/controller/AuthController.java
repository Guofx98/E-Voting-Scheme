package com.example.caserver.controller;

import com.example.caserver.entity.Auth;
import com.example.caserver.entity.IdentityMsg;
import com.example.caserver.entity.Key;
import com.example.caserver.entity.Result;
import com.example.caserver.utils.BlindSignature;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.Objects;

@RestController
public class AuthController {

    @Autowired
    Contract contract;

    @Autowired
    MongoTemplate mongoTemplate;

    @PostMapping("/auth")
    public Result auth(@RequestBody IdentityMsg identityMsg) throws ContractException {
        if(identityMsg.getBlinded()==null)
            return Result.error("盲消息为空！");
        System.out.println(identityMsg);

        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("communityId").is(identityMsg.getCommunityId()),Criteria.where("identity").is(identityMsg.getIdentity()),Criteria.where("room").is(identityMsg.getRoom()));
        Query query = new Query(criteria);
        Auth one = mongoTemplate.findOne(query, Auth.class);
        if(one==null){
            return Result.error("未找到该户！");
        }
        if(one.getIdentity().equals(identityMsg.getIdentity())&&one.getIsAuth())
            return Result.error("您已经注册!");
        if(!one.getIdentity().equals(identityMsg.getIdentity()))
            return Result.error("您没有权限注册！");
        Update update = new Update();
        update.set("isAuth",true);
        mongoTemplate.updateFirst(query, update, Auth.class);
        BigInteger signed = BlindSignature.sign(new BigInteger(identityMsg.getBlinded()), Objects.requireNonNull(mongoTemplate.findById("BlindSignPrivateKey_"+identityMsg.getCommunityId(), Key.class)));
        return Result.success(signed.toString());
    }

}
