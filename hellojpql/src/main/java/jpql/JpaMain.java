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
            createTeamAndMember(em);

            em.flush();
            em.clear();
            


            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void createQueryUsingNamedQuery(EntityManager em) {
        final List<Member> resultList = em.createNamedQuery("Member.findByUsername", Member.class)
                .setParameter("username", "Kate")
                .getResultList();

        for (Member member : resultList) {
            System.out.println("member = " + member);
        }
    }

    private static void createQueryUsingEntityDirectly(EntityManager em) {
        final Member kate = em.find(Member.class, 4L);
        final Team teamA = em.find(Team.class, 1L);

        String query = "Select m From Member m where m.team = :team";

        List<Member> members = em.createQuery(query, Member.class)
                .setParameter("team", teamA)
                .getResultList();

        for (Member member : members) {
            System.out.println("member = " + member);
        }
    }

    private static void createQueryUsingJpqlFunction(EntityManager em) {
        String query = "select locate('de', 'abcdef') from Member m";
        String query2 = "select size(t.members) from Team t";

        final List<Integer> result = em.createQuery(query, Integer.class).getResultList();
        for (Integer name : result) {
            System.out.println("name = " + name);
        }
    }

    private static void createQueryUsingCoalesceAndNullif(EntityManager em) {
        final Member member = new Member();
        member.setUsername("관리자");  // target
        member.setAge(99);
        em.persist(member);

        String query =
                "select coalesce(m.username, '이름 없는 회원') from Member m";

        String query2 =
                "select nullif(m.username, '관리자') from Member m";

        final List<String> result = em.createQuery(query2, String.class).getResultList();
        for (String name : result) {
            System.out.println("name = " + name);
        }
    }

    private static void createQueryUsingCase(EntityManager em) {
        String query =
                "select " +
                        "case when m.age <= 10 then '학생요금' " +
                        "when m.age >= 60 then '경로요금'" +
                        "else '일반요금' " +
                        "end " +
                        "from Member m";
        final List<String> result = em.createQuery(query, String.class).getResultList();

        for (String fee : result) {
            System.out.println("fee = " + fee);
        }
    }

    private static void createTeamAndMember(EntityManager em) {
        final Team teamA = new Team();
        teamA.setName("teamA");
        em.persist(teamA);

        final Team teamB = new Team();
        teamB.setName("teamB");
        em.persist(teamB);


        final Member member = new Member();
        member.setUsername("Joy");
        member.setAge(10);
        member.setTeam(teamA);
        member.setType(MemberType.USER);
        em.persist(member);

        final Member member2 = new Member();
        member2.setUsername("Kate");
        member2.setAge(20);
        member2.setTeam(teamA);
        member2.setType(MemberType.USER);
        em.persist(member2);

        final Member member3 = new Member();
        member3.setUsername("Dorothy");
        member3.setAge(30);
        member3.setTeam(teamB);
        member3.setType(MemberType.USER);
        em.persist(member3);
    }

    private static void createQueryWithEnumType(EntityManager em) {
        String query = "select m.username, 'HELLO', true from Member m " +
                "where m.type = :userType";
        List<Object[]> result = em.createQuery(query)
                .setParameter("userType", MemberType.ADMIN)
                .getResultList();

        for (Object[] objects : result) {
            System.out.println("objects = " + objects[0]);
            System.out.println("objects = " + objects[1]);
            System.out.println("objects = " + objects[2]);
        }

    }

    private static void createSubQuery(EntityManager em) {
        String query = "select (select avg(m1.age) from Member m1) from Member m left join Team t on m.username = t.name";
        List<Member> result = em.createQuery(query, Member.class).getResultList();
        System.out.println("result.size() = " + result.size());
    }

    private static void createQueryWithJoin(EntityManager em) {
        // select m from Member m inner join m.team t
        // select m from Member m left outer join m.team t
        // select m from Member m, Team t where m.username = t.name
        // select m from Member m left join m.team t on t.name = 'teamA'

        String query = "select m from Member m left join Team t on m.username = t.name";
        List<Member> result = em.createQuery(query, Member.class).getResultList();
        System.out.println("result.size() = " + result.size());
    }

    private static void createQueryWithPaging(EntityManager em) {
        List<Member> result = em.createQuery("select m from Member m order by m.age desc", Member.class)
                .setFirstResult(0)
                .setMaxResults(10)
                .getResultList();

        System.out.println("result.size() = " + result.size());
        for (Member member : result) {
            System.out.println("member = " + member);
        }
    }

    private static void createQueryUsingMemberDto(EntityManager em) {
        // "select m.team from Member m", Member.class
        // "select t from Member m join m.team t", Team.class

        final List<MemberDto> result = em.createQuery("select new jpql.MemberDto(m.username, m.age) from Member m", MemberDto.class)
                .getResultList();

        for (MemberDto memberDto : result) {
            System.out.println("username = " + memberDto.getUsername());
            System.out.println("age = " + memberDto.getAge());
        }
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
