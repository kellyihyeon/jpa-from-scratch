package jpa.jpashop.web;

import jpa.jpashop.domain.Member;
import jpa.jpashop.domain.item.Item;
import jpa.jpashop.repositoty.OrderRepository;
import jpa.jpashop.service.ItemService;
import jpa.jpashop.service.MemberService;
import jpa.jpashop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    MemberService memberService;

    @Autowired
    ItemService itemService;

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public String createForm(Model model) {
        List<Member> members = memberService.findMembers(); // 예시를 위해 회원정보를 보여줌
        List<Item> items = itemService.findItems(); // 어떤 상품이 있는지 상품정보 보여주기

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "/order/orderForm";
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public String order(
            @RequestParam("memberId") Long memberId,
            @RequestParam("itemId") Long itemId,
            @RequestParam("count") int count) {

        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }
}
