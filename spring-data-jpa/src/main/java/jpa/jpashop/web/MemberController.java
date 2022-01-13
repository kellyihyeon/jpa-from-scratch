package jpa.jpashop.web;

import jpa.jpashop.domain.Member;
import jpa.jpashop.repository.MemberRepository;
import jpa.jpashop.servoce.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MemberController {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @RequestMapping("member/memberUpdateForm")
    public String memberUpdateForm(@RequestParam("id") Long id, Model model) { // vs @Param
        Member member = memberRepository.findOne(id);
        model.addAttribute("member", member);
        return "member/memberSaveForm";
    }

    @RequestMapping("member/memberUpdateForm")
    public String sampleDomainClassConverter(@RequestParam("id") Member member, Model model) {
        model.addAttribute("member", member);
        return "member/memberSaveForm";
    }

    @RequestMapping(value = "/members", method = RequestMethod.GET)
    public String list(Pageable pageable, Model model) {

        Page<Member> page = memberService.findMembers(pageable);
        model.addAttribute("members", page.getContent());
        return "members/memberList";
    }
}
