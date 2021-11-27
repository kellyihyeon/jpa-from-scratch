package com.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ExamMergeMain {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("my_jpa");

    public static void main(String[] args) {

        final Member member = createMember("memberA", "회원1");

        member.setUsername("jpa회원1");

        mergeMember(member);
    }


    private static Member createMember(String id, String username) {

        final EntityManager manager1 = emf.createEntityManager();
        final EntityTransaction transaction1 = manager1.getTransaction();

        transaction1.begin();
        final Member member = new Member();
        member.setId(id);
        member.setUsername(username);

        manager1.persist(member);
        transaction1.commit();

        // persistence context - close
        manager1.close();

        return member;
    }

    private static void mergeMember(Member member) {
        final EntityManager manager2 = emf.createEntityManager();
        final EntityTransaction transaction2 = manager2.getTransaction();

        transaction2.begin();
//        final Member mergeMember = manager2.merge(member);
        member = manager2.merge(member);    // 준영속 엔티티를 참조하던 변수를 영속 엔티티를 참조하도록 변경하는 것이 안전
        transaction2.commit();

        System.out.println("준영속 상태의 member = " + member.getUsername());

//        System.out.println("다시 영속 상태가 된 mergeMember = " + mergeMember.getUsername());
        System.out.println("영속 엔티티를 참조하도록 한 member = " + member.getUsername());

        System.out.println("manager2.contains(member) = " + manager2.contains(member));
//        System.out.println("manager2.contains(mergeMember) = " + manager2.contains(mergeMember));

        manager2.close();
    }
}

