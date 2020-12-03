package com.itsdits.grocerylist.repository;

import com.itsdits.grocerylist.model.Grocery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroceryRepository extends JpaRepository<Grocery, Long> {

    List<Grocery> findByPurchased(boolean purchased);
    List<Grocery> findByFavorite(boolean favorite);
    List<Grocery> findByNameContaining(String name);
    List<Grocery> findAllByUserId(String id);
}
