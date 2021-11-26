package com.jpa;

import javax.persistence.EntityManager;

public class DetachedEntity {

    public static void detached(EntityManager em) {
        final Member member = new Member();
        member.setId("memberA");
        member.setUsername("회원A");

        em.persist(member);
        final Member findMember = em.find(Member.class, member.getId());
        System.out.println("영속성 컨텍스트가 관리: findMember = " + findMember);

        em.detach(member);
        final Member detachedMember = em.find(Member.class, member.getId());
        System.out.println("영속성 컨텍스트에서 분리: detachedMember = " + detachedMember);

        System.out.println("객체로서의 member = " + member);
    }
}
