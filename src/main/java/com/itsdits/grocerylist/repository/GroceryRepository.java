package com.itsdits.grocerylist.repository;

import com.itsdits.grocerylist.model.Grocery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroceryRepository extends JpaRepository<Grocery, Long> {
}
