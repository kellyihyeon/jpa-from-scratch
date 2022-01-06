//package jpa.model.entity;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "ORDERS")
//public class Order {
//
//    @Id
//    private String id;
//
//    @ManyToOne
//    private Member member;
//
//    public Order() {
//    }
//
//    public Order(String id, Member member) {
//        this.id = id;
//        this.member = member;
//    }
//
//    public Member getMember() {
//        return member;
//    }
//
//    public void setMember(Member member) {
//        this.member = member;
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
//    @Override
//    public String toString() {
//        return "Order{" +
//                "id='" + id + '\'' +
//                ", member=" + member +
//                '}';
//    }
//}
