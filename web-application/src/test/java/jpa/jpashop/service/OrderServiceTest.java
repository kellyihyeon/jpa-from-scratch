package jpa.jpashop.service;

import jpa.jpashop.domain.Address;
import jpa.jpashop.domain.Member;
import jpa.jpashop.domain.Order;
import jpa.jpashop.domain.OrderStatus;
import jpa.jpashop.domain.item.Book;
import jpa.jpashop.domain.item.Item;
import jpa.jpashop.exception.NotEnoughStockException;
import jpa.jpashop.repositoty.OrderRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:appConfig.xml")
@Transactional
public class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;


    @Test
    public void 상품주문() {
        // given
        Member member = createMember();
        Item item = createBook("JPA Programming", 43000, 30);
        int orderCount = 2;

        // when
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // then
        Order getOrder = orderRepository.findOne(orderId);

        // params = String message, Object expected, Object actual
        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.", 43000 * 2, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다.", 28, item.getStockQuantity());
    }

    @Test(expected = NotEnoughStockException.class)
    public void  상품주문_재고수량초과() {
        // given
        Member member = createMember();
        Item item = createBook("JPA Programming", 43000, 30);
        int orderCount = 31;    // 재고 30 < 주문 수량 31

        // when
        orderService.order(member.getId(), item.getId(), orderCount);

        // then
        fail("재고 수량 부족 예외가 발생해야 합니다.");
    }

    @Test
    public void 주문취소() {
        // given
        Member member = createMember();
        Item item = createBook("JPA Programming", 43000, 30);
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // when
        System.out.println("dirty checking 발생 전 재고 수량 = " + item.getStockQuantity());
        orderService.cancelOrder(orderId);
        System.out.println("dirty checking 발생 후 재고 수량 = " + item.getStockQuantity());

        // then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals("주문 취소시 상태는 CANCEL 이다.", OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("주문이 취소된 상품은 주문수량만큼 재고가 증가해야 한다.", 30, item.getStockQuantity());
    }

    
    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("Kate");
        member.setAddress(new Address("서울", "29st", "123-123"));
        em.persist(member);
        return member;
    }

}