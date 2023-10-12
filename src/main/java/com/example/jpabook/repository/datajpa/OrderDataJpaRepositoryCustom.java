package com.example.jpabook.repository.datajpa;

import com.example.jpabook.domain.Order;
import com.example.jpabook.repository.OrderSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderDataJpaRepositoryCustom {
    Page<Order> findOrders(OrderSearch orderSearch, Pageable pageable);


}
