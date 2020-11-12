package com.itsdits.grocerylist.service;

import com.itsdits.grocerylist.model.Grocery;
import com.itsdits.grocerylist.repository.GroceryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GroceryService {

    @Autowired
    private GroceryRepository repo;

    public List<Grocery> getAll() {
        return repo.findAll();
    }

    public Grocery get(long id) {
        return repo.findById(id).orElse(null);
    }

    public List<Grocery> getNotPurchased() {
        return repo.findByPurchased(false);
    }

    public List<Grocery> getPurchased() {
        return repo.findByPurchased(true);
    }

    public List<Grocery> getByName(String name) {
        return repo.findByName(name);
    }

    public Grocery save(Grocery grocery) {
        return repo.save(grocery);
    }

    public void delete(long id) {
        repo.deleteById(id);
    }

    public void purge() {
        repo.deleteAll();
    }
}
