package com.example.caserver.entity;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;

@Data
public class Proposal {
    private String proposalId;
    private String deadline;
    private String type;
    private String communityId;
    private String managerId;
    private String topic;
    private String desc;
    private List<LinkedHashMap> selections;
    private Boolean hasResult;
    private String docType;
}
