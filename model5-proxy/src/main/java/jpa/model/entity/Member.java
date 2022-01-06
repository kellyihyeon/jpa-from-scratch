//package jpa.model.entity;
//
//import javax.persistence.*;
//import java.util.List;
//
//@Entity
//public class Member {
//
//    @Id
//    @Column(name = "MEMBER_ID")
//    private String id;
//
//    private String username;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    private Team team;
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
//    private List<Order> orders;
//
//
//
//
//    public List<Order> getOrders() {
//        return orders;
//    }
//
//    public void setOrders(List<Order> orders) {
//        this.orders = orders;
//    }
//
//    public Member(String id, String username) {
//        this.id = id;
//        this.username = username;
//    }
//
//    public Member() {
//
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public Team getTeam() {
//        return team;
//    }
//
//    public void setTeam(Team team) {
//        this.team = team;
//    }
//
//    @Override
//    public String toString() {
//        return "Member{" +
//                "id='" + id + '\'' +
//                ", username='" + username + '\'' +
//                ", team=" + team +
//                ", orders=" + orders +
//                '}';
//    }
//}
