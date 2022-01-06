package jpa.model.entity;

import javax.persistence.*;

@Entity
public class Team {

    @Id
    private String id;

    private String name;


    public Team(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Team() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
