package com.jpa;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        // persistence unit: 일반적으로 연결할 데이터베이스당 하나의 영속성 유닛 등록
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("my_jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            // manage transaction
            transaction.begin();
            logic(entityManager);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("message = " + e);
            transaction.rollback();
        } finally{
            entityManager.close();
        }
        entityManagerFactory.close();

    }

    // business logic
    private static void logic(EntityManager em) {
        String id = "nid1";
        Member member = new Member();
        member.setId(id);
        member.setUsername("jay");
        member.setAge(28);
        System.out.println("member = " + member);

        // 등록
        em.persist(member);

        // 수정
        member.setAge(280);

        final Member findMember = em.find(Member.class, id);
        System.out.println("findMember.getUsername() = " + findMember.getUsername());
        System.out.println("findMember.getAge() = " + findMember.getAge());

        final List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("members.size() = " + members.size());

        em.remove(member);
    }
}
