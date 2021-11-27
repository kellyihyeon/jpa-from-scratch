package com.jpa;

import javax.persistence.*;

public class JpaMain {

    public static void main(String[] args) {
        // persistence unit: 일반적으로 연결할 데이터베이스당 하나의 영속성 유닛 등록
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("my_jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            // manage transaction
            transaction.begin();

            // business logic
            BusinessLogic.logic(entityManager);

            // persistence context - detach(), clear(), close()
            DetachedEntity.detached(entityManager);

            ClearExam.clearPersistenceContext(entityManager);

            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("message = " + e);
            transaction.rollback();
        } finally{

            // 영속성 컨텍스트 종료
            entityManager.close();
        }
        entityManagerFactory.close();

    }
}
