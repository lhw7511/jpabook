package com.example.jpabook.repository.datajpa;


import com.example.jpabook.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemDataJpaRepository extends JpaRepository<Item,Long> {

}
