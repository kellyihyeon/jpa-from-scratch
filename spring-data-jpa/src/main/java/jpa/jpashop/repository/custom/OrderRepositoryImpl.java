package jpa.jpashop.repository.custom;

import com.mysema.query.jpa.JPQLQuery;
import jpa.jpashop.domain.Order;
import jpa.jpashop.domain.OrderSearch;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.List;

public class OrderRepositoryImpl extends QueryDslRepositorySupport implements CustomOrderRepository {


    public OrderRepositoryImpl() {
        super(Order.class); // QueryDslRepositorySupport 에 엔티티 클래스 정보 넘기기
    }

    @Override
    public List<Order> search(OrderSearch orderSearch) {
        // 쿼리 타입(Q): 학습 전이므로 흐름 정도만 이해하고 넘어갈 것
        // QueryDslRepositorySupport 과 QueryDslPredicateExecutor<Item> 차이
        QOrder order = QOrder.order;
        QMember member = QMember.member;

        JPQLQuery query = from(order);

        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query.leftJoin(order.member, member).where(member.name.contains(orderSearch.getMemberName()));
        }

        if (orderSearch.getOrderStatus() != null) {
            query.where(order.status.eq(orderSearch.getOrderStatus()));
        }

        return query.list(order);

        return null;
    }
}
