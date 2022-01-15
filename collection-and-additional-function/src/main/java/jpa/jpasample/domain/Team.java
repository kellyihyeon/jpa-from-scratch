package jpa.jpasample.domain;

import jpa.jpasample.domain.Member;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Team {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "team")
    @OrderBy("username desc, id asc")
    private Set<Member> members = new HashSet<>();



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Member> getMembers() {
        return members;
    }

    public void setMembers(Set<Member> members) {
        this.members = members;
    }


}
