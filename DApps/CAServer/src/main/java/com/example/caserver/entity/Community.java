package com.example.caserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document("Communities")
@AllArgsConstructor
@NoArgsConstructor
public class Community {
    @Id
    private String id;

    @Field
    private String name;

    @Field
    private String managerId;

}
