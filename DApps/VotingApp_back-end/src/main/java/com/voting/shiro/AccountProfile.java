package com.voting.shiro;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AccountProfile implements Serializable {
    private String id;
    private String userName;
    private String role;
    private String managerId;
    private String status;
    private String phone;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String communityId;
}
