package com.practice;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("my_jpa");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            // business logic
            testSave(em);
//            queryLogicJoin(em);
//            updateRelation(em);
//            deleteRelation(em);
//            biDirection(em);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();

    }

    public static void testSave(EntityManager em) {
        final Team team1 = new Team("team1", "팀1");
        em.persist(team1);

        final Member member1 = new Member("member1", "회원1");
        // member1.team 연관관계 설정
        member1.setTeam(team1);
        em.persist(member1);

        final Member member2 = new Member("member2", "회원2");
        // member1.team 연관관계 설정
        member2.setTeam(team1);
        em.persist(member2);

        final Member findMember = em.find(Member.class, "member1");
        // 객체 그래프 탐색
        final Team team = findMember.getTeam();
        System.out.println("팀 이름 = " + team.getName());

        System.out.println("size = " + team.getMembers().size());

    }

    private static void queryLogicJoin(EntityManager em) {

        String jpql = "select m from Member m join m.team t where " + "t.name=:teamName";
        final List<Member> resultList = em.createQuery(jpql, Member.class)
                                            .setParameter("teamName", "팀1")
                                            .getResultList();

        for (Member member : resultList) {
            System.out.println("[query] member.getUsername() = " + member.getUsername());
            System.out.println("member = " + member);
        }

    }

    // 새로운 팀2로 수정
    private static void updateRelation(EntityManager em) {
        final Team team2 = new Team("team2", "팀2");
        em.persist(team2);

        final Member member1 = em.find(Member.class, "member1");
        member1.setTeam(team2);
        System.out.println("member1 = " + member1);
    }

    private static void deleteRelation(EntityManager em) {
        final Member member1 = em.find(Member.class, "member1");
        member1.setTeam(null);
        System.out.println("member1 = " + member1);
    }

    private static void biDirection(EntityManager em) {

        final Team team = em.find(Team.class, "team1");

        final List<Member> members = team.getMembers();
        for (Member member : members) {
            System.out.println("member.getUsername() = " + member.getUsername());
        }

    }

}
