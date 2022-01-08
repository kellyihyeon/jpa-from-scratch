package jpql;

import javax.persistence.*;
import java.util.List;


public class JpaMain {

    public static void main(String[] args) {
        //엔티티 매니저 팩토리 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("my_jpa");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            // business logic
            createMember(em);

            em.flush();
            em.clear();

            // "select m.team from Member m", Member.class
            // "select t from Member m join m.team t", Team.class

            final List<MemberDto> result = em.createQuery("select new jpql.MemberDto(m.username, m.age) from Member m", MemberDto.class)
                    .getResultList();

            for (MemberDto memberDto : result) {
                System.out.println("username = " + memberDto.getUsername());
                System.out.println("age = " + memberDto.getAge());
            }


            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void createQueryUsingSetParameterMethod(EntityManager em) {
        final Member singleResult = em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", "member1")
                .getSingleResult();

        System.out.println("singleResult = " + singleResult.getUsername());
    }

    private static void UsingGetSingleResultMethod(EntityManager em) {
        TypedQuery<Member> query4 = em.createQuery("select m from Member m where m.id = 5", Member.class);
        final Member result = query4.getSingleResult();
        // Spring Data Jpa -> 결과가 없으면 exception 을 터뜨리지 않는다.
        System.out.println("result = " + result);
    }

    private static void createQuery(EntityManager em) {
        TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
        TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);
        Query query3 = em.createQuery("select m.username, m.age from Member m");

        final List<Member> resultList = query1.getResultList();
        for (Member member1 : resultList) {
            System.out.println("member = " + member1);
        }
    }

    private static void createMember(EntityManager em) {
        Member member = new Member();
        member.setUsername("member1");
        member.setAge(10);
        em.persist(member);

        Member member2 = new Member();
        member2.setUsername("member2");
        member2.setAge(20);
        em.persist(member2);
    }

}
