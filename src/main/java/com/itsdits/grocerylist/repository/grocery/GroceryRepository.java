package com.itsdits.grocerylist.repository.grocery;

import com.itsdits.grocerylist.model.grocery.Grocery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroceryRepository extends JpaRepository<Grocery, Long> {

    List<Grocery> findByPurchased(boolean purchased);
    List<Grocery> findByNameContaining(String name);

}
