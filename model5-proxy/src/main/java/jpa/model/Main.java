package jpa.model;

import jpa.model.entity.Member;
import jpa.model.entity.Order;
import jpa.model.entity.Team;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("my_jpa");
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            // business logic
            createMember(em);
//            printUserAndTeam(em);
//            printOnlyUser(em);
            printCollectionRapper(em);


            transaction.commit();
            System.out.println("commit 성공");
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            System.out.println("error 발생: rollback");
        } finally {
            em.close();
        }
        emf.close();

    }

    private static void printCollectionRapper(EntityManager em) {
        final Member member = em.find(Member.class, "멤버1");
        System.out.println("member = " + member);   // order = null

        final Order order = new Order("주문1", member);
        em.persist(order);
        System.out.println("order = " + order);
//
//        final List<Order> orders = member.getOrders();
//        System.out.println("orders.getClass().getName() = " + orders.getClass().getName());
    }

    private static void printOnlyUser(EntityManager em) {
        final Member member = em.find(Member.class, "멤버1");
        System.out.println("회원 아이디 = " + member.getId());
        System.out.println("회원 이름 = " + member.getUsername());
    }

    private static void printUserAndTeam(EntityManager em) {
        final Member member = em.find(Member.class, "멤버1");
        final Team team = member.getTeam();
        System.out.println("회원 이름 = " + member.getUsername());
        System.out.println("소속팀 = " + team.getName());
    }

    private static void createMember(EntityManager em) {
        final Team team1 = new Team("팀1", "teamTiger");
        em.persist(team1);  // 컨텍스트에 넣고

        final Member member1 = new Member("멤버1", "가르시아");
        final Member member2 = new Member("멤버2", "케이트");
        member1.setTeam(team1);
        em.persist(member1);    // 컨텍스트에 넣고
        em.persist(member2);

    }


}
