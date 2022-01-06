package jpa.model;

import jpa.model.entity.*;

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

            saveWithCascade(em);


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

    private static void saveWithCascade(EntityManager em) {
        final Child child1 = new Child();
        final Child child2 = new Child();

        final Parent parent = new Parent();
        child1.setParent(parent);
        child2.setParent(parent);

        parent.getChildren().add(child1);
        parent.getChildren().add(child2);

        em.persist(parent);
        System.out.println("child1에서 parent id 접근 = " + child1.getParent().getId());
        System.out.println("child2에서 parent id 접근 = " + child2.getParent().getId());

        final Parent findParent = em.find(Parent.class, 1L);
        em.remove(findParent);

    }

    private static void saveNoCascade(EntityManager em) {
        final Parent parent = new Parent();
        em.persist(parent);

        final Child child1 = new Child();
        child1.setParent(parent);      // 자식 -> 부모와 연관관계 설정
        // parent.getChildren() 할 때 이미 null 아니냐고 ->  parent.getChildren() = [] !!!!!
        System.out.println("parent.getChildren() = " + parent.getChildren());
        parent.getChildren().add(child1);   // 부모 -> 자식
        em.persist(child1);

        final Child child2 = new Child();
        child2.setParent(parent);
        parent.getChildren().add(child2);
        em.persist(child2);
    }

    private static void printCollectionRapper(EntityManager em) {
        final Member member = em.find(Member.class, "멤버1");
        System.out.println("member = " + member);   // order = null

        final Order order = new Order("주문1", member);
        em.persist(order);

        System.out.println("order = " + order);
        /**
         *
         */
        final List<Order> orders = member.getOrders();
        System.out.println("orders.getClass().getName() = " + orders.getClass().getName());
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
