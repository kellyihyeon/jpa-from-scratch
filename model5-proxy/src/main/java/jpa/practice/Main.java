package jpa.practice;

import jpa.practice.entity.Delivery;
import jpa.practice.entity.Order;
import jpa.practice.entity.OrderItem;

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
//            beforeUsingCascade(em);
            afterUsingCascade(em);

            transaction.commit();
            System.out.println("commit 성공");
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            System.out.println("error 발생: rollback");
        } finally {
            em.close();
        }
        emf.close();

    }

    private static void afterUsingCascade(EntityManager em) {
        final Delivery delivery = new Delivery();
        final OrderItem orderItem1 = new OrderItem();
        final OrderItem orderItem2 = new OrderItem();

        final Order order = new Order();
        order.setDelivery(delivery);
        order.addOrderItem(orderItem1);
        order.addOrderItem(orderItem2);

        em.persist(order);
    }

    private static void beforeUsingCascade(EntityManager em) {
        final Delivery delivery = new Delivery();
        em.persist(delivery);

        final OrderItem orderItem1 = new OrderItem();
        final OrderItem orderItem2 = new OrderItem();
        em.persist(orderItem1);
        em.persist(orderItem2);

        final Order order = new Order();
        order.setDelivery(delivery);
        order.addOrderItem(orderItem1);
        order.addOrderItem(orderItem2);

        em.persist(order);
    }

}
