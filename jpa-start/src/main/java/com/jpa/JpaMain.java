package com.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        // persistence unit: 일반적으로 연결할 데이터베이스당 하나의 영속성 유닛 등록
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("my_jpa");

        final EntityManager entityManager = entityManagerFactory.createEntityManager();

        final EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            logic(entityManager);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally{
            entityManager.close();
        }
        entityManagerFactory.close();

    }

    // business logic
    private static void logic(EntityManager em) {

    }
}
