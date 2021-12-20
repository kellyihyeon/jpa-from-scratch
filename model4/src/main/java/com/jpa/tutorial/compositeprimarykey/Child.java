package com.jpa.tutorial.compositeprimarykey;

import javax.persistence.*;

@Entity
public class Child {    // Parent 와 비식별관계

    @Id
    private String id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "PARENT_ID1",referencedColumnName = "PARENT_ID1"),
            @JoinColumn(name = "PARENT_ID2", referencedColumnName = "PARENT_ID2")
    })
    private Parent parent;
}
