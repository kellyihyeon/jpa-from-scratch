package jpa.practice;

import jpa.model.entity.Address;
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


    private static void usingImmutableObject(EntityManager em) {
        final Member member = new Member();
        member.setName("Kate");
        member.setHomeAddress(new Address("NewYork", "29st", "CA"));
        em.persist(member);

        // Address = 불변 객체
        final Address homeAddress = member.getHomeAddress();
        final Address newAddress = new Address(homeAddress.getCity(), "84", homeAddress.getState());

        final Member anotherMember = new Member();
        anotherMember.setHomeAddress(newAddress);
        em.persist(anotherMember);

        System.out.println("member = " + member);
        System.out.println("anotherMember = " + anotherMember);
    }
}
