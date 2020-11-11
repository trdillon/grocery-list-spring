package com.itsdits.grocerylist.repository;

import com.itsdits.grocerylist.model.Grocery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroceryRepository extends JpaRepository<Grocery, Long> {

    List<Grocery> findByPurchased(boolean purchased);
    List<Grocery> findByName(String name);

}
