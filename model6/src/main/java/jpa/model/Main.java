package jpa.model;

import jpa.model.entity.Address;
import jpa.model.entity.Member;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("my_jpa");
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            // business logic
            System.out.println("비지니스 로직 시작!");

            usingValueTypeCollection(em);

            updateValueTypeCollection(em);

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

    private static void updateValueTypeCollection(EntityManager em) {
        final Member member = em.find(Member.class, 1L);

        // 임베디드 값 타입 수정 (통영 -> 송도)
        member.setHomeAddress(new Address("송도", "new street", "S98"));

        // 기본 값 타입 컬렉션 수정
        final Set<String> favoriteFoods = member.getFavoriteFoods();
        favoriteFoods.remove("깐풍기");
        favoriteFoods.add("팔보채");

        // 임베디드 값 타입 컬렉션 수정
        final List<Address> addressHistory = member.getAddressHistory();
        addressHistory.remove(new Address("서울", "강남", "S45"));
        addressHistory.add(new Address("전주", "칼국수거리", "J734"));

        System.out.println("member = " + member);
        for (String favoriteFood : favoriteFoods) {
            System.out.println("favoriteFood = " + favoriteFood);
        }
        for (Address address : addressHistory) {
            System.out.println("address = " + address);
        }
    }

    private static void getValueTypeCollection(EntityManager em) {
        final Member member = em.find(Member.class, 1L);
        final Address homeAddress = member.getHomeAddress();
        final Set<String> favoriteFoods = member.getFavoriteFoods();

        for (String favoriteFood : favoriteFoods) {
            System.out.println("favoriteFood = " + favoriteFood);
        }
        final List<Address> addressHistory = member.getAddressHistory();
        System.out.println("address history 1번 = " + addressHistory.get(0));
    }

    private static void usingValueTypeCollection(EntityManager em) {
        final Member member = new Member();
        // Address = 임베디드 값 타입
        member.setHomeAddress(new Address("통영", "몽돌해수욕장", "T"));

        // Set<String> favoriteFoods = 기본값 타입 컬렉션
        member.getFavoriteFoods().add("짬뽕");
        member.getFavoriteFoods().add("간짜장");
        member.getFavoriteFoods().add("깐풍기");

        // List<Address> addressHistory = 임베디드 값 타입 컬렉션
        member.getAddressHistory().add(new Address("서울", "강남", "S45"));
        member.getAddressHistory().add(new Address("서울", "강북", "S46"));

        em.persist(member);
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
