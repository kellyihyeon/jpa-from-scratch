package jpa.model;

import jpa.model.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("my_jpa");
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            // business logic
            System.out.println("비지니스 로직 시작!");
            final Member member = new Member();
            member.setName("가르시아");
            member.setCompanyAddress(null);

            em.persist(member);
            System.out.println("member = " + member);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error = " + e);
            transaction.rollback();
        }finally {
            em.close();
        }
        emf.close();


    }
}
