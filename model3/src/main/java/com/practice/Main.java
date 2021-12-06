package com.practice;

import javax.persistence.*;

public class Main {


    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("my_jpa");
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            // business logic
            testSave(em);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }

    public static void testSave(EntityManager em) {
        final Member member1 = new Member("member1");
        final Member member2 = new Member("member2");

        final Team team1 = new Team("team1");
        team1.getMembers().add(member1);
        team1.getMembers().add(member2);

        em.persist(member1);
        em.persist(member2);
        em.persist(team1);
    }
}
