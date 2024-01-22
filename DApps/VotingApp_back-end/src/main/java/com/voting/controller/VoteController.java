package com.voting.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.voting.common.dto.VoteDto;
import com.voting.common.lang.Result;
import com.voting.entity.blockchain.Key;
import com.voting.entity.blockchain.Proposal;
import com.voting.entity.blockchain.PublicParam;
import com.voting.util.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/vote")
public class VoteController {
    @Autowired
    private Contract contract;

    @RequiresAuthentication
    @PostMapping("/createVote")
    public Result CreateVote(@RequestBody HashMap<String,Object>map) throws ContractException, InterruptedException, TimeoutException {
        Proposal proposal = new Proposal();
        proposal.setProposalId(UUID.randomUUID().toString());
        proposal.setCommunityId(ShiroUtil.getProfile().getCommunityId());
        proposal.setManagerId(ShiroUtil.getProfile().getManagerId());
        Instant instant = Instant.parse(map.get("deadline").toString());
        proposal.setDeadline(LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Shanghai")).toString());
        proposal.setDesc((String) map.get("desc"));
        String type = (String) map.get("type");
        proposal.setType(type);
        String topic = (String) map.get("topic");
        if(topic==null||"".equals(topic))
            topic="车位分配方案";
        proposal.setTopic(topic);
        List<LinkedHashMap> result = new ArrayList<>();
        if("allocate".equals(type)){
            Object obj = map.get("scheme");
            if (obj instanceof ArrayList<?>) {
                for (Object o : (List<?>) obj) {
                    result.add(LinkedHashMap.class.cast(o));
                }
            }
        }
        else if("custom".equals(type)){
            Object obj = map.get("selections");
            if (obj instanceof ArrayList<?>) {
                for (Object o : (List<?>) obj) {
                    result.add(LinkedHashMap.class.cast(o));
                }
            }
        }
        proposal.setSelections(result);
        proposal.setDocType("proposal");
        proposal.setHasResult(false);
        String res = new String(contract.submitTransaction("createVote",proposal.getProposalId(), JSONObject.toJSONString(proposal)));
        Result r = JSONObject.parseObject(res, Result.class);
        if(r.getCode()!=200)
            return Result.error(r.getMsg());
        //发送请求创建投票密钥
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1,new StringHttpMessageConverter(StandardCharsets.UTF_8));
        String url = "https://5y3278d124.oicp.vip:443/createVoteKey?proposalId="+proposal.getProposalId();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json;charset=UTF-8"));
        HttpEntity<Map<String,Object>> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return JSONObject.parseObject(response.getBody(), Result.class);
    }

    @RequiresAuthentication
    @GetMapping("/queryProposal")
    public Result queryProposal(@RequestParam(value = "communityId") String communityId,@RequestParam(value = "isOverTime") Boolean isOverTime) throws ContractException{
        String res = new String(contract.evaluateTransaction("queryProposal",communityId));
        Result result = JSONObject.parseObject(res,Result.class);
        List<Proposal> list = new ArrayList<>();
        for (Object datum : (JSONArray) result.getData()){
            String data = (String) datum;
            Proposal proposal = JSONObject.parseObject(data,Proposal.class);
            LocalDateTime dateTime = LocalDateTime.parse(proposal.getDeadline());
            LocalDateTime now = LocalDateTime.now();
            boolean r = dateTime.isAfter(now);
            if(isOverTime&&!r)
                list.add(proposal);
            else if(!isOverTime&&r)
                list.add(proposal);
        }
        return Result.success(list);
    }

    @GetMapping("/getSignPublicKey")
    public Result getSignPublicKey(@RequestParam(value = "communityId")String communityId) throws ContractException {
        String res = new String(contract.evaluateTransaction("getSignPublicKey",communityId));
        Result result = JSONObject.parseObject(res,Result.class);
        String r = (String) result.getData();
        Key key = JSONObject.parseObject(r, Key.class);
        return Result.success(key);
    }

    @GetMapping("/getPublicParam")
    public Result getPublicParam() throws ContractException {
        String res = new String(contract.evaluateTransaction("getPublicParam"));
        Result result = JSONObject.parseObject(res,Result.class);
        String r = (String) result.getData();
        PublicParam publicParam = JSONObject.parseObject(r, PublicParam.class);
        return Result.success(publicParam);
    }

    @GetMapping("/setVoteToken")
    public Result setVoteToken(@RequestParam(value = "voteId") String voteId, @RequestParam(value = "sign") String sign,@RequestParam(value = "communityId") String communityId) throws ContractException, InterruptedException, TimeoutException {
        String res = new String(contract.submitTransaction("setVoteToken",voteId,sign,communityId));
        return JSONObject.parseObject(res,Result.class);
    }

    @GetMapping("/getProposalById")
    public Result getProposalById(@RequestParam(value = "proposalId")String proposalId) throws ContractException {
        String res = new String(contract.evaluateTransaction("getProposalById",proposalId));
        Result result = JSONObject.parseObject(res,Result.class);
        if(result.getCode()!=200)
            return Result.error(result.getMsg());
        return result;
    }

    @GetMapping("/getVotePublicKey")
    public Result getVotePublicKey(@RequestParam(value = "proposalId")String proposalId) throws ContractException {
        String res = new String(contract.evaluateTransaction("getVotePublicKey",proposalId));
        return JSONObject.parseObject(res,Result.class);

    }

    @PostMapping("/submitVote")
    public Result submitVote(@RequestBody VoteDto voteDto) throws ContractException, InterruptedException, TimeoutException {
        String res = new String(contract.submitTransaction("vote",voteDto.getPK(),voteDto.getB(),voteDto.getC(),voteDto.getR(),voteDto.getProposalId(),voteDto.getContent()));
        return JSONObject.parseObject(res,Result.class);
    }

    @GetMapping("/getCountResult")
    public Result getCountResult(@RequestParam(value = "proposalId") String proposalId) throws ContractException {
        String res = new String(contract.evaluateTransaction("getCountResult",proposalId));
        return JSONObject.parseObject(res,Result.class);
    }
}
