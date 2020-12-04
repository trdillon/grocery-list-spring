package com.itsdits.grocerylist.controller;

import com.itsdits.grocerylist.exception.ResourceNotFoundException;
import com.itsdits.grocerylist.model.Grocery;
import com.itsdits.grocerylist.service.GroceryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class GroceryController {

    private final Logger log = LoggerFactory.getLogger(GroceryController.class);
    private final GroceryService groceryService;

    public GroceryController(GroceryService groceryService) {
        this.groceryService = groceryService;
    }

    @GetMapping("/groceries")
    Collection<Grocery> groceries() {
        return groceryService.getAll();
    }

    @GetMapping("/grocery/{id}")
    ResponseEntity<?> getGrocery(@PathVariable Long id) {
        Optional<Grocery> grocery = Optional.ofNullable(groceryService.getById(id));
        return grocery.map(response -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new ResourceNotFoundException("No grocery with the ID " + id + " was found."));
    }
}
