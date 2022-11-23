package com.example.jpabook.repository.datajpa;

import com.example.jpabook.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDataJpaRepository extends JpaRepository<Order,Long>, OrderDataJpaRepositoryCustom{
}
