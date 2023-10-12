package com.example.jpabook.repository.datajpa;

import com.example.jpabook.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderDataJpaRepository extends JpaRepository<Order,Long>, OrderDataJpaRepositoryCustom{


    @Query(value = "SELECT o FROM Order o LEFT JOIN FETCH o.delivery ORDER BY o.id DESC",
            countQuery = "SELECT count(o) FROM Order o")
    Page<Order> findOrderPage(Pageable pageable);
}
