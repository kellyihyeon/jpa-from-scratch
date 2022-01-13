package jpa.jpashop.repository;

import jpa.jpashop.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m where m.username = :name")
    Member findByUsername(@Param("name") String username);

    Page<Member> findByUsernameStartingWith(String name, Pageable pageable);
}
