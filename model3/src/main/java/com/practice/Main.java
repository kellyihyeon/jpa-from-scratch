package com.practice;

import javax.persistence.*;
import java.time.LocalDate;

public class Main {


    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("my_jpa");
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            // business logic
            save(em);
            find(em);

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


    public static void save(EntityManager em) {
        // 회원 저장
        final Member member1 = new Member();
        member1.setId("member1");
        member1.setUsername("회원1");
        em.persist(member1);
        System.out.println("persist - member");

        // 상품 저장
        final Product productA = new Product();
        productA.setId("productA");
        productA.setName("상품1");
        em.persist(productA);
        System.out.println("persist - product");

        // 주문 저장
        final Orders orders = new Orders();
        orders.setMember(member1);
        orders.setProduct(productA);
        orders.setOrdersAmount(2);
        orders.setOrdersDate(LocalDate.now());
        em.persist(orders);
        System.out.println("orders.getId() = " + orders.getId());
        System.out.println("persist - order");
    }


    public static void find(EntityManager em) {
        Long orderId = 1L;
        final Orders orders = em.find(Orders.class, orderId);

        final Member member = orders.getMember();
        final Product product = orders.getProduct();

        System.out.println("member.getUsername() = " + member.getUsername());
        System.out.println("product.getName() = " + product.getName());
        System.out.println("orders.getOrderAmount() = " + orders.getOrdersAmount());
        System.out.println("orders.getOrderDate() = " + orders.getOrdersDate());
    }


}
