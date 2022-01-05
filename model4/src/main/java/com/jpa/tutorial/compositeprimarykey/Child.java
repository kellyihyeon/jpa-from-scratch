package com.jpa.tutorial.compositeprimarykey;

import javax.persistence.*;

@Entity
public class Child {

    @Id
    @GeneratedValue
    @Column(name = "CHILD_ID")
    private Long id;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
