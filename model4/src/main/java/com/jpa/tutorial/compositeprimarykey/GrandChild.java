package com.jpa.tutorial.compositeprimarykey;

import javax.persistence.*;

@Entity
public class GrandChild {

    @EmbeddedId     // pk
    private GrandChildId id;

    @MapsId("childId")   // pk
    @ManyToOne          // fk
    @JoinColumns({
            @JoinColumn(name = "PARENT_ID"),
            @JoinColumn(name = "CHILD_ID")
    })
    private Child child;


    private String name;



    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public GrandChildId getId() {
        return id;
    }

    public void setId(GrandChildId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
