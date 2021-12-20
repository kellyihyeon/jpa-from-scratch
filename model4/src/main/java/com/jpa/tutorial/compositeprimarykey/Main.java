package com.jpa.tutorial.compositeprimarykey;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("my_jpa");
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            // business logic
            final Parent parent = new Parent();
            parent.setId1("myId1");
            parent.setId2("myId2");
            parent.setName("parentName");

            final Parent parent2 = new Parent();
            parent2.setId1("myId2");
            parent2.setId2("myId9");
            parent2.setName("parentName2");

            em.persist(parent);
            em.persist(parent2);

            final ParentId parentId = new ParentId("myId1", "myId2");
            final Parent findParent = em.find(Parent.class, parentId);
            System.out.println("findParent.getName() = " + findParent.getName());


            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();


    }
}
