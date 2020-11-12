package com.itsdits.grocerylist.service.grocery;

import com.itsdits.grocerylist.model.grocery.Grocery;
import com.itsdits.grocerylist.repository.grocery.GroceryRepository;
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

    public Grocery getById(long id) {
        return repo.findById(id).orElse(null);
    }

    public List<Grocery> getNotPurchased() {
        return repo.findByPurchased(false);
    }

    public List<Grocery> getPurchased() {
        return repo.findByPurchased(true);
    }

    public List<Grocery> getByName(String name) {
        return repo.findByNameContaining(name);
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
