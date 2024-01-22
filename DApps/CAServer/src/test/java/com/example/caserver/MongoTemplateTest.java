package com.example.caserver;

import com.example.caserver.entity.Auth;
import com.example.caserver.entity.Community;
import com.mongodb.client.result.UpdateResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

public class MongoTemplateTest extends CaServerApplicationTests{

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void test(){
        boolean AuthList = mongoTemplate.collectionExists("AuthList");
        if(AuthList)
            mongoTemplate.dropCollection("AuthList");
        mongoTemplate.createCollection("AuthList");
    }

    @Test
    public void insert(){

    }

    @Test
    public void find(){
        List<Auth> all = mongoTemplate.findAll(Auth.class);
        Auth auth = mongoTemplate.findById("1", Auth.class);
        System.out.println(auth);

        Query query = new Query(Criteria.where("id").is("1"));
        List<Auth> auths = mongoTemplate.find(query, Auth.class);
        System.out.println(auths);

    }

    @Test
    public void update(){
        Query query = new Query(Criteria.where("id").is("1"));
        Update update = new Update();
        update.set("state",true);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Auth.class);
        System.out.println(updateResult);
    }

    @Test
    public void remove(){
        Query query = new Query(Criteria.where("id").is("1"));
        mongoTemplate.remove(query,Auth.class);
    }
}
