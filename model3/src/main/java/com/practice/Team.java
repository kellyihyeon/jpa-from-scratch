package com.practice;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    @OneToMany
    @JoinColumn(name = "TEAM_ID")   // 일대다 단방향 연관관계
    private List<Member> members = new ArrayList<>();


    public Team() { }

    public Team(String name) {
        this.name = name;
    }

    //    public void addMemeber(Member member) {
//        this.members.add(member);
//        if (member.getTeam() != this) {
//            member.setTeam(this);
//        }
//    }


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

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
