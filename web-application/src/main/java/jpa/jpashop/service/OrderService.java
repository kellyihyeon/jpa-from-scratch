package jpa.jpashop.service;

import jpa.jpashop.domain.*;
import jpa.jpashop.domain.item.Item;
import jpa.jpashop.repositoty.MemberRepository;
import jpa.jpashop.repositoty.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemService itemService;    // itemRepository 에서 갖고오지 않고 왜 service 계층에 접근한 걸까?

    public Long order(Long memberId, Long itemId, int count) {

        Member member = memberRepository.findOne(memberId); // Kate
        Item item = itemService.findOne(itemId);    // Book

        Delivery delivery = new Delivery(member.getAddress());  // 멤버 주소로 배송 엔티티를 생성

        // order 와 item - 파라미터로 왜 order 는 안받지?
        // item, item 의 가격, item 의 갯수 -> 거의 item 관련인데
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        Order order = Order.createOrder(member, delivery, orderItem);
        orderRepository.save(order);
        return order.getId();
    }

    public void cancelOrder(Long orderId) {
         Order order = orderRepository.findOne(orderId);
         order.cancel();
    }

    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch);
    }
}
