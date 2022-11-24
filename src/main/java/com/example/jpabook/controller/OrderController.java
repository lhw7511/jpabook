package com.example.jpabook.controller;

import com.example.jpabook.domain.Member;
import com.example.jpabook.domain.Order;
import com.example.jpabook.domain.item.Item;
import com.example.jpabook.dto.OrderDto;
import com.example.jpabook.repository.OrderSearch;
import com.example.jpabook.service.ItemService;
import com.example.jpabook.service.MemberService;
import com.example.jpabook.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;
    
    @GetMapping("/order")
    public String createForm(Model model){
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members",members);
        model.addAttribute("items",items);

        return "order/orderForm";

    }
    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count){
        orderService.order(memberId,itemId,count);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model, @PageableDefault(size = 10, page = 0) Pageable pageable){
        Page<Order> result = orderService.findOrders(orderSearch,pageable);
        Page<OrderDto> orders = result.map(o -> new OrderDto(o));
        model.addAttribute("orders",orders);
        return "order/orderList";
    }

    @GetMapping("/orders/api")
    public @ResponseBody Page<OrderDto>  orderListApi(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model, @PageableDefault(size = 10, page = 0) Pageable pageable){
        Page<Order> result = orderService.findOrders(orderSearch,pageable);
        Page<OrderDto> orders = result.map(o -> new OrderDto(o));

        return orders;
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId){
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}
