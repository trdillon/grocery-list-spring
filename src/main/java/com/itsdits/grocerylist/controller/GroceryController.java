package com.itsdits.grocerylist.controller;

import com.itsdits.grocerylist.model.Grocery;
import com.itsdits.grocerylist.service.GroceryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class GroceryController {

    @Autowired
    private GroceryService service;

    @GetMapping("/grocery")
    public ResponseEntity<List<Grocery>> getGroceryList(@RequestParam(required = false) String name) {
        try {
            List<Grocery> groceries = new ArrayList<>();

            // Get all groceries unless a search name was passed
            if (name == null) {
                groceries.addAll(service.getAll());
            } else {
                groceries.addAll(service.getByName(name));
            }

            // Return the List or no content response
            if (groceries.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(groceries, HttpStatus.OK);
            }
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/grocery/{id}")
    public ResponseEntity<Grocery> getGroceryById(@PathVariable("id") long id) {
        Optional<Grocery> grocery = Optional.ofNullable(service.get(id));

        return grocery.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("grocery/need")
    public ResponseEntity<List<Grocery>> getNotPurchased() {
        try {
            List<Grocery> groceries = service.getNotPurchased();

            if (groceries.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(groceries, HttpStatus.OK);
            }
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("grocery/purchased")
    public ResponseEntity<List<Grocery>> getPurchased() {
        try {
            List<Grocery> groceries = service.getPurchased();

            if (groceries.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(groceries, HttpStatus.OK);
            }
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
