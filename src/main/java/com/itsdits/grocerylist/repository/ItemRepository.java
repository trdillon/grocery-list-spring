package com.itsdits.grocerylist.repository;

import com.itsdits.grocerylist.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findAllByUserId(String id, Pageable pageable);
}
