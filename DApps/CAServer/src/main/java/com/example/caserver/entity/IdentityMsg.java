package com.example.caserver.entity;

import lombok.Data;

@Data
public class IdentityMsg {
    private String communityId;
    private String identity;
    private String room;
    private String blinded;
}
