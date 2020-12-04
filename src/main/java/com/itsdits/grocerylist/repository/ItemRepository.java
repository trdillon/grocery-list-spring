package com.itsdits.grocerylist.repository;

import com.itsdits.grocerylist.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByUserId(String id);
    List<Item> findByProductContaining(String product);
    List<Item> findByPurchased(boolean purchased);
    List<Item> findByFavorite(boolean favorite);
}
