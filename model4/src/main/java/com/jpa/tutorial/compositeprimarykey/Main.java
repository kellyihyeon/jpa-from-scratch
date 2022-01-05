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
            final ParentId parentId = new ParentId("myId1", "myId2");

            em.persist(parent);

            final Parent findParent = em.find(Parent.class, parentId);
            System.out.println("findParent.getName() = " + findParent.getName());

            final ParentId parentIdCopy = new ParentId("myId1", "myId2");
            System.out.println("equals method test = " + parentId.equals(parentIdCopy));
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
