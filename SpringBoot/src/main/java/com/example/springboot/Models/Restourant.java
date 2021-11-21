package com.example.springboot.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class Restourant {

    @Id
    @Field(name = "id")
    private String id;
    private String RestourantName;

    public Restourant(String id, String restourantName) {
        this.id = id;
        RestourantName = restourantName;
    }

    public Restourant() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRestourantName() {
        return RestourantName;
    }

    public void setRestourantName(String restourantName) {
        RestourantName = restourantName;
    }
}
