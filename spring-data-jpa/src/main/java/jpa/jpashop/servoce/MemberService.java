package jpa.jpashop.servoce;

import jpa.jpashop.domain.Member;
import jpa.jpashop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional  // tx:annotation-driven 활성화 해야 할텐데
public class MemberService {

    @Autowired
    MemberRepository memberRepository;

    public Page<Member> findMembers(Pageable pageable) {
        return memberRepository.findByUsernameStartingWith("Kate", pageable);
    }
}
