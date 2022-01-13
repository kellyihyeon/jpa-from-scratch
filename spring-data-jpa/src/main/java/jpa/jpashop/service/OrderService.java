package jpa.jpashop.service;

import jpa.jpashop.domain.*;
import jpa.jpashop.domain.item.Item;
import jpa.jpashop.repository.MemberRepository;
import jpa.jpashop.repository.OrderRepository;
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
    ItemService itemService;

    public Long order(Long memberId, Long itemId, int count) {

        Member member = memberRepository.findOne(memberId); // Kate
        Item item = itemService.findOne(itemId);    // Book

        Delivery delivery = new Delivery(member.getAddress());

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
        // JpaSpecificationExecutor<T>.findAll(Specification<T> spec);
        return orderRepository.findAll(orderSearch.toSpecification());
    }
}
