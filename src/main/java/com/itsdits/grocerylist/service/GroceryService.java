package com.itsdits.grocerylist.service;

import com.itsdits.grocerylist.model.Grocery;
import com.itsdits.grocerylist.repository.GroceryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GroceryService {

    private final GroceryRepository groceryRepository;

    public GroceryService(GroceryRepository groceryRepository) {
        this.groceryRepository = groceryRepository;
    }

    public Page<Grocery> getAll(Pageable pageable) {
        return groceryRepository.findAll(pageable);
    }

    public Page<Grocery> getByName(String name, Pageable pageable) {
        return groceryRepository.findByNameContaining(name, pageable);
    }

    public Grocery getById(long id) {
        return groceryRepository.findById(id).orElse(null);
    }
/*
    public List<Grocery> getByGroup(String group) {
        return groceryRepository.findByGroupContaining(group);
    }

    public List<Grocery> getBySubGroup(String subgroup) {
        return groceryRepository.findBySubGroupContaining(subgroup);
    }

 */
}
