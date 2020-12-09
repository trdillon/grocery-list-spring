package com.itsdits.grocerylist.repository;

import com.itsdits.grocerylist.model.Grocery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroceryRepository extends JpaRepository<Grocery, Long> {
    Page<Grocery> findAll(Pageable pageable);
    Page<Grocery> findByNameContaining(String name, Pageable pageable);
    Page<Grocery> findByGroup(String group, Pageable pageable);
    Page<Grocery> findBySubGroup(String subgroup, Pageable pageable);
    List<Grocery> findByNameContaining(String name, Sort sort);
    List<Grocery> findByGroupContaining(String group, Sort sort);
    List<Grocery> findBySubGroupContaining(String subgroup, Sort sort);
}
