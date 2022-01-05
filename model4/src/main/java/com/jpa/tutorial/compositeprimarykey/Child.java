package com.jpa.tutorial.compositeprimarykey;

import javax.persistence.*;

@Entity
public class Child {

    @EmbeddedId     // pk
    private ChildId id;

    @MapsId("parentId") // pk
    @ManyToOne          // fk
    @JoinColumn(name = "PARENT_ID")
    public Parent parent;


    private String name;


    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public ChildId getId() {
        return id;
    }

    public void setId(ChildId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
