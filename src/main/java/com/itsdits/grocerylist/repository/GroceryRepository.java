package com.itsdits.grocerylist.repository;

import com.itsdits.grocerylist.model.Grocery;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroceryRepository extends JpaRepository<Grocery, Long> {
    Page<Grocery> findAll(Example example, Pageable pageable);
    Page<Grocery> findByNameContaining(String name, Pageable pageable);
}
