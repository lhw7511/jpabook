package com.example.jpabook.service;

import com.example.jpabook.domain.item.Item;
import com.example.jpabook.repository.ItemRepository;
import com.example.jpabook.repository.datajpa.ItemDataJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemDataJpaRepository itemDataJpaRepository;
    @Transactional
    public void saveItem(Item item){
        itemDataJpaRepository.save(item);
    }

    public List<Item> findItems(){
        return itemDataJpaRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemDataJpaRepository.findById(itemId).get();
    }


    public void updateItem(Long itemId, String name, int price){
        Item findItem = itemDataJpaRepository.findById(itemId).get();
        findItem.setName(name);
        findItem.setPrice(price);
    }
}
