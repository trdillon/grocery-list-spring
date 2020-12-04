package com.itsdits.grocerylist.service;

import com.itsdits.grocerylist.model.Grocery;
import com.itsdits.grocerylist.repository.GroceryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GroceryService {

    private final GroceryRepository groceryRepository;

    public GroceryService(GroceryRepository groceryRepository) {
        this.groceryRepository = groceryRepository;
    }

    public List<Grocery> getAll() {
        return groceryRepository.findAll();
    }

    public Grocery getById(long id) {
        return groceryRepository.findById(id).orElse(null);
    }

    public List<Grocery> getByName(String name) {
        return groceryRepository.findByNameContaining(name);
    }

    public List<Grocery> getByGroup(String group) {
        return groceryRepository.findByGroupContaining(group);
    }

    public List<Grocery> getBySubGroup(String subgroup) {
        return groceryRepository.findBySubGroupContaining(subgroup);
    }
}
