package com.jpa.tutorial.compositeprimarykey;

import javax.persistence.*;

@Entity
public class Parent {

    @Id     // pk
    @Column(name = "PARENT_ID")
    private String id;

    private String name;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
