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

    // 다대다: 연결엔티티 - 저장
    public static void save(EntityManager em) {
        // 회원 저장
        final Member member1 = new Member();
        member1.setId("member1");
        member1.setUsername("회원1");
        em.persist(member1);

        // 상품 저장
        final Product productA = new Product();
        productA.setId("productA");
        productA.setName("상품1");
        em.persist(productA);

        // 회원상품 저장
        // member, product, orderAmount, orderDate
        final MemberProduct memberProduct = new MemberProduct();
        memberProduct.setMember(member1);
        memberProduct.setProduct(productA);
        memberProduct.setOrderAmount(2);
        memberProduct.setOrderDate(LocalDate.now());

        em.persist(memberProduct);
    }

    // 다대다: 연결엔티티 - 조회
    public static void find(EntityManager em) {
        // 복합 기본 키 값 생성
        final MemberProductId memberProductId = new MemberProductId();
        memberProductId.setMember("member1");
        memberProductId.setProduct("productA");

        final MemberProduct memberProduct = em.find(MemberProduct.class, memberProductId);
        final Member member = memberProduct.getMember();
        final Product product = memberProduct.getProduct();

        System.out.println("member.getUsername() = " + member.getUsername());
        System.out.println("product.getName() = " + product.getName());
        System.out.println("memberProduct.getOrderAmount() = " + memberProduct.getOrderAmount());
        System.out.println("memberProduct.getOrderDate() = " + memberProduct.getOrderDate());
    }


}
