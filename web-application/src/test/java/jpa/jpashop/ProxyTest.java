package jpa.jpashop;

import jpa.jpashop.domain.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:appConfig.xml")
@Transactional
public class ProxyTest {

    @PersistenceContext
    EntityManager em;


    private void createMemberAndFlushAndClear() {
        Member member = new Member("Kate");
        em.persist(member);
        em.flush();
        em.clear();
    }

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


}
