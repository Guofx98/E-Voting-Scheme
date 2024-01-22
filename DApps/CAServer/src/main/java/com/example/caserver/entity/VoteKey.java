package com.example.caserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document("VoteKey")
@AllArgsConstructor
@NoArgsConstructor
public class VoteKey {
    @Id
    private String name;

    @Field
    private String proposalId;

    @Field
    private String N;

    @Field
    private String D;

    @Field
    private String E;

    @Field
    private String BASE64;

}
