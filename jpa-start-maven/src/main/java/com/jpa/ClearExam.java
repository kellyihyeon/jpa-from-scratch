package com.jpa;

import javax.persistence.EntityManager;

public class ClearExam {

    public static void clearPersistenceContext(EntityManager em) {
        final Member memberOne = new Member();
        memberOne.setId("memberA");

        final Member memberTwo = new Member();
        memberTwo.setId("memberB");

        em.persist(memberOne);
        em.persist(memberTwo);

        final Member findMemberOne = em.find(Member.class, "memberA");
        System.out.println("findMemberOne = " + findMemberOne);

        em.clear();
        System.out.println("Persistence Context - clear");

        findMemberOne.setUsername("clearedMember");     // 객체에 매핑

        final Member findMemberOneAfterClear = em.find(Member.class, "memberA");
        System.out.println("findMemberOneAfterClear = " + findMemberOneAfterClear);
    }

}
