package com.jpa.tutorial.compositeprimarykey;

import javax.persistence.*;

@Entity
public class GrandChild {

    @Id
    @GeneratedValue
    @Column(name = "GRANDCHILD_ID")
    private Long id;


    @ManyToOne // fk
    @JoinColumn(name = "CHILD_ID")
    private Child child;


    private String name;



    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
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
