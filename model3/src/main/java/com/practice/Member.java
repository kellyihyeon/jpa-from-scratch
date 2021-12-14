package com.practice;

import javax.persistence.*;
import java.util.List;

@Entity
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    private String id;

    private String username;


    @OneToMany(mappedBy = "member")
    private List<MemberProduct> memberProducts;


    @OneToOne(mappedBy = "member")
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;


    public Member() { }

    public Member(String username) {
        this.username = username;
    }


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
