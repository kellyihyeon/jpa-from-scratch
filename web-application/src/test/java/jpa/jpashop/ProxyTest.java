package jpa.jpashop;

import jpa.jpashop.domain.Member;
import jpa.jpashop.domain.OrderItem;
import jpa.jpashop.domain.item.Book;
import jpa.jpashop.domain.item.Item;
import org.hibernate.proxy.HibernateProxy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:appConfig.xml")
@Transactional
public class ProxyTest {

    @PersistenceContext
    EntityManager em;


    @Test
    @Transactional
    public void 영속성컨텍스트_동일성보장_프록시_먼저_조회() {
        createMemberAndFlushAndClear();

        Member refMember = em.getReference(Member.class, 1L);   // 영속성 컨텍스트에 엔티티가 없으니까 프록시로 반환
        Member findMember = em.find(Member.class, 1L);  // 영속성 컨텍스트의 엔티티 반환?

        System.out.println("refMember.getClass()  = " + refMember.getClass());
        System.out.println("findMember.getClass() = " + findMember.getClass());

        assertTrue(refMember == findMember);
        // 성공한다
        //refMember.getClass()  = class jpa.jpashop.domain.Member_$$_jvst4c_7
        //findMember.getClass() = class jpa.jpashop.domain.Member_$$_jvst4c_7
   }

   @Test
   @Transactional
   public void 영속성컨텍스트_동일성보장_원본엔티티_먼저_조회() {
       Member member = new Member("Anne");
       em.persist(member);
       em.flush();
       em.clear();

       Member findMember = em.find(Member.class, 1L);
       Member refMember = em.getReference(Member.class, 1L);

       System.out.println("findMember.getClass() = " + findMember.getClass());
       System.out.println("refMember.getClass()  = " + refMember.getClass());

       assertSame(findMember, refMember);
       //findMember.getClass() = class jpa.jpashop.domain.Member
       //refMember.getClass()  = class jpa.jpashop.domain.Member
   }

    @Test
    @Transactional
    public void 프록시_타입비교() {
        createMemberAndFlushAndClear();

        Member refMember = em.getReference(Member.class, 1L);
        System.out.println("refMember.getClass() = " + refMember.getClass());

        assertFalse(Member.class == refMember.getClass());
        assertTrue(refMember instanceof Member);
    }


    @Test
    @Transactional
    public void 프록시_동등성비교() {
        Member saveMember = new Member("Anne");
        em.persist(saveMember);
        em.flush();
        em.clear();

        Member newMember = new Member("Anne");  // 내용이 같은 객체 생성
        Member refMember = em.getReference(Member.class, 1L);   // saveMember 프록시
        System.out.println("newMember.getName() = " + newMember.getName());
        System.out.println("refMember.getName() = " + refMember.getName());

        assertEquals(newMember, refMember); // equals() 구현함

    }

    @Test
    public void 부모타입으로_프록시조회() {
        // given
        Book saveBook = new Book();
        saveBook.setName("yeongHan's_book");
        saveBook.setAuthor("kim");
        em.persist(saveBook);

        em.flush();
        em.clear();

        // when
        // 부모타입으로 프록시 조회
        Item proxyItem = em.getReference(Item.class, saveBook.getId());
        System.out.println("proxyItem = " + proxyItem.getClass());

        if (proxyItem instanceof Book) {    // 하위 타입으로 상위 타입의 여부를 검사한다? -> false !
            System.out.println("proxyItem instanceof Book");
            Book book = (Book) proxyItem;   // 강제 타입 변환 -> if 제거하더라도 ClassCastException
            System.out.println("저자 = " + book.getAuthor());
        }

        // then
        assertFalse(proxyItem.getClass() == Book.class);    // 패스
        assertFalse(proxyItem instanceof Book); // pass
        assertTrue(proxyItem instanceof Item); // pass
    }

    @Test
    public void 상속관계와_프록시_도메인모델() {
        // given
        Book book = new Book();
        book.setName("yeongHan's_book");
        book.setAuthor("kim");
        em.persist(book);

        OrderItem saveOrderItem = new OrderItem();
        saveOrderItem.setItem(book);
        em.persist(saveOrderItem);
        // orderItem2 - album

        em.flush();
        em.clear();

        // when
        OrderItem orderItem = em.find(OrderItem.class, saveOrderItem.getId());
        Item item = orderItem.getItem();    // 전략: 지연로딩 -> 프록시 반환 (item)

        System.out.println("item = " + item.getClass());    // 프록시 객체

        // then
        assertFalse(item.getClass() == Book.class);     // pass
        assertFalse(item instanceof Book);      // pass
        assertTrue(item instanceof Item);       // pass? Item proxy 랑 Item 객체 -> 상속받은 거니까 pass
    }

    @Test
    public void 프록시_벗기기() {
        // given
        Book book = new Book();
        book.setName("yeongHan's_book");
        book.setAuthor("kim");
        em.persist(book);

        OrderItem saveOrderItem = new OrderItem();
        saveOrderItem.setItem(book);
        em.persist(saveOrderItem);
        // orderItem2 - album

        em.flush();
        em.clear();

        // when
        OrderItem orderItem = em.find(OrderItem.class, saveOrderItem.getId());
        orderItem.printItem();  // Item 구현체의 title 출력

        Item item = orderItem.getItem();    // 전략: 지연로딩 -> 프록시 반환 (item)
        Item unProxyItem = unProxy(item);   // 프록시를 벗기고 왜 Item 으로 받는 거지...똑같은 거 아닌가

        System.out.println("orderItem.getItem().getClass() = " + orderItem.getItem().getClass());   // Item_$$_jvstcb_5 <- 프록시가 반환
        System.out.println("unProxy(item).getClass()       = " + unProxy(item).getClass());         // Book <- 구현체가 반환


        if (unProxyItem instanceof Book) {
            System.out.println("proxyItem instanceof Book");
            Book unProxyBook = (Book) unProxyItem;
            System.out.println("저자 = " + unProxyBook.getAuthor());
        }


        // then
        assertTrue(item != unProxyItem);
        assertFalse(item == unProxyItem);   //
    }



    // 멤버 생성한 후 flush -> clear
    private void createMemberAndFlushAndClear() {
        Member member = new Member("Kate");
        em.persist(member);
        em.flush();
        em.clear();
    }

    // 프록시에서 원본 엔티티 찾는 메소드
    private static <T> T unProxy(Object entity) {
        if (entity instanceof HibernateProxy) {
            entity = ((HibernateProxy) entity).getHibernateLazyInitializer().getImplementation();
        }
        return (T) entity;
    }

}
