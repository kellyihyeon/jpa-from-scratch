package jpa.model;

import jpa.model.entity.Address;
import jpa.model.entity.Member;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("my_jpa");
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // business logic
            createMember(em);
            createQuerySqlUsingNativeSql(em);

            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error = " + e);
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void createQuerySqlUsingNativeSql(EntityManager em) {
        // native query
        final Member member = new Member();
        member.setName("Ella");
        em.persist(member);     // db 에 들어가 있지 않은 상태

        // 어떻게 db 에서 꺼내오지??
        // flush -> em.commit() 했을 때 그리고 query 날아갈 때 flush 동작
        final List<Member> resultList = em.createNativeQuery("select MEMBER_ID, city, street, zipcode, NAME from MEMBER", Member.class)
                .getResultList();

        for (Member member1 : resultList) {
            System.out.println("member1 = " + member1);
        }

    }

    private static void createQueryUsingCriteriaWithDynamicLogic(EntityManager em) {
        final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        final CriteriaQuery<Member> query = criteriaBuilder.createQuery(Member.class);

        final Root<Member> member = query.from(Member.class);

        CriteriaQuery<Member> criteriaQuery = query.select(member);

        String name = "anonymous";
        if (name != null) {
            criteriaQuery = criteriaQuery.where(criteriaBuilder.equal(member.get("name"), "kate"));
        }

        final List<Member> resultList = em.createQuery(criteriaQuery).getResultList();
        for (Member m : resultList) {
            System.out.println("member = " + m);
        }
    }

    private static void createQueryUsingCriteria(EntityManager em) {
        // sql 문을 자바 코드로 짜고 있다.
        final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        final CriteriaQuery<Member> query = criteriaBuilder.createQuery(Member.class);
        final Root<Member> member = query.from(Member.class);
        final CriteriaQuery<Member> criteriaQuery = query.select(member).where(criteriaBuilder.equal(member.get("name"), "kate"));
        em.createQuery(criteriaQuery).getResultList();
    }

    private static void createQueryUsingJpql(EntityManager em) {
        // sql 과 다른 점: 객체를 대상으로 하고 있다. select member 멤버 자체를 선택
        final List<Member> result = em.createQuery("select m FROM Member m where m.name like '%kate%'", Member.class)
                .getResultList();

        for (Member member : result) {
            System.out.println("member = " + member);
        }
    }

    private static void createMember(EntityManager em) {
        final Member kate = new Member();
        kate.setName("kate");
        kate.setAddress(new Address("서울", "광화문", "78"));
        em.persist(kate);

        final Member rosie = new Member();
        rosie.setName("rosie");
        em.persist(rosie);

        final Member christine = new Member();
        christine.setName("christine");
        em.persist(christine);

        final Member kate2 = new Member();
        kate2.setName("kate");
        kate2.setAddress(new Address("청주", "청주동1가", "342"));
        em.persist(kate2);
    }

}
