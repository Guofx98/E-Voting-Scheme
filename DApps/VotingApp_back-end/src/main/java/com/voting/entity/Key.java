package com.voting.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("key")
public class Key {

    @TableId
    private String proposalId;

    private String privateKey;

}
