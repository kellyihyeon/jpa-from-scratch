package jpa.jpashop.domain;

import org.springframework.data.jpa.domain.Specifications;

import static jpa.jpashop.domain.OrderSpec.memberNameLike;
import static jpa.jpashop.domain.OrderSpec.orderStatusEq;
import static org.springframework.data.jpa.domain.Specifications.where;

public class OrderSearch {

    private String memberName;

    private OrderStatus orderStatus;

    // Specification 생성하는 코드
    public Specifications<Order> toSpecification() {
        return where(memberNameLike(memberName)).and(orderStatusEq(orderStatus));
    }


    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
