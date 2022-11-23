package com.example.jpabook.service;

import com.example.jpabook.domain.Delivery;
import com.example.jpabook.domain.Member;
import com.example.jpabook.domain.Order;
import com.example.jpabook.domain.OrderItem;
import com.example.jpabook.domain.item.Item;
import com.example.jpabook.repository.ItemRepository;
import com.example.jpabook.repository.MemberRepository;
import com.example.jpabook.repository.OrderRepository;
import com.example.jpabook.repository.OrderSearch;
import com.example.jpabook.repository.datajpa.ItemDataJpaRepository;
import com.example.jpabook.repository.datajpa.MemberDataJpaRepository;
import com.example.jpabook.repository.datajpa.OrderDataJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final MemberDataJpaRepository memberDataJpaRepository;

    private final ItemDataJpaRepository itemDataJpaRepository;

    private final OrderDataJpaRepository orderDataJpaRepository;

    //주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count){
        Member member = memberDataJpaRepository.findById(memberId).get();
        Item item = itemDataJpaRepository.findById(itemId).get();

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem =  OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문생성
        Order order = Order.createOrder(member, delivery, orderItem);

        orderDataJpaRepository.save(order);

        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId){
       Order order = orderDataJpaRepository.findById(orderId).get();
       order.cancel();
    }

    public Page<Order> findOrders(OrderSearch orderSearch){
        PageRequest pageRequest = PageRequest.of(0,100);
        return orderDataJpaRepository.findOrders(orderSearch,pageRequest);
    }
}
