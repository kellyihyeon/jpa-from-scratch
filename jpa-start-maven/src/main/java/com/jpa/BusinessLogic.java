package com.jpa;

import javax.persistence.EntityManager;
import java.util.List;

public class BusinessLogic {

    public static void logic(EntityManager em) {
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
        final Member findMember2 = em.find(Member.class, id);
        System.out.println("findMember = " + findMember);
        System.out.println("영속성 컨텍스트는 엔티티의 동일성을 보장한다 = " + (findMember == findMember2));
        System.out.println(findMember.hashCode());
        System.out.println(findMember2.hashCode());

        final List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("members.size() = " + members.size());

        em.remove(member);
    }
}
