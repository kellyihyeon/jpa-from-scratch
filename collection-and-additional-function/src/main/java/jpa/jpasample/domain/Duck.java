package jpa.jpasample.domain;

import jpa.jpasample.DuckListener;

import javax.persistence.*;

@Entity
@EntityListeners(DuckListener.class)
public class Duck {

    @Id
    @GeneratedValue
    private Long id;

    private String name;


    // 이벤트 직접 받기
    @PrePersist
    public void prePersist() {
        System.out.println("Duck.perPersist id = " + id);
    }

    @PostPersist
    public void postPersist() {
        System.out.println("Duck.postPersist id = " + id);
    }

    @PostLoad
    public void postLoad() {
        System.out.println("Duck.postLoad");
    }

    @PreRemove
    public void preRemove() {
        System.out.println("Duck.preRemove");
    }

    @PostRemove
    public void postRemove() {
        System.out.println("Duck.postRemove");
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
