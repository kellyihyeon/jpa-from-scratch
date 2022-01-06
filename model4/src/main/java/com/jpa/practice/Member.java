package com.jpa.practice;

import com.jpa.practice.entity.BaseEntity;
import javax.persistence.*;

@Entity
public class Member extends BaseEntity {

    @Id
    private String id;

    private String username;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
