package com.example.caserver.schedule;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.caserver.entity.Proposal;
import com.example.caserver.entity.Result;
import com.example.caserver.entity.VoteKey;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.concurrent.TimeoutException;

@Configuration
@EnableScheduling
public class CountTask {

    @Autowired
    Contract contract;
    @Autowired
    MongoTemplate mongoTemplate;

    @Scheduled(fixedRate=30000)
    private void configureTasks() throws ContractException, InterruptedException, TimeoutException {
        String res = new String(contract.evaluateTransaction("queryProposalNoResult"));
        Result result = JSONObject.parseObject(res,Result.class);
        for (Object datum : (JSONArray) result.getData()){
            String data = (String) datum;
            Proposal proposal = JSONObject.parseObject(data,Proposal.class);
            LocalDateTime dateTime = LocalDateTime.parse(proposal.getDeadline());
            LocalDateTime now = LocalDateTime.now();
            boolean r = dateTime.isAfter(now);
            if(!r){
                VoteKey voteKey = mongoTemplate.findById("VotePrivateKey_" + proposal.getProposalId(), VoteKey.class);
                String s = new String(contract.submitTransaction("count",proposal.getProposalId(),voteKey.getBASE64()));
                System.out.println(s);
            }
        }
    }
}
