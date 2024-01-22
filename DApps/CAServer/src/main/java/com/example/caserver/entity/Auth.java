package com.example.caserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("AuthList")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Auth {
    @Id
    private String id;
    @Field
    private String communityId;
    @Field
    private String identity;
    @Field
    private String room;
    @Field
    private Boolean isAuth;

    @Override
    public boolean equals(Object obj) {
        // 首先判断传进来的obj是否是调用equals方法对象的this本身，提高判断效率
        if (obj == this) {return true;}
        // 判断传进来的obj是否是null，提高判断效率
        if (obj == null) {return false;}
        // 判断传进来的obj是否是User对象，防止出现类型转换的异常
        if (obj instanceof Auth) {
            Auth auth = (Auth) obj;
            boolean flag = this.communityId.equals(auth.communityId) && this.identity == auth.identity && this.room == auth.room;
            return flag;
        }
        // 如果没有走类型判断语句说明两个比较的对象它们的类型都不一样，结果就是false了
        return false;
    }
}
