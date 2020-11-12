package com.itsdits.grocerylist.controller;

import com.itsdits.grocerylist.model.Grocery;
import com.itsdits.grocerylist.service.GroceryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        Optional<Grocery> grocery = Optional.ofNullable(service.getById(id));

        return grocery.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/grocery/need")
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

    @GetMapping("/grocery/purchased")
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

    @PostMapping("/grocery")
    public ResponseEntity<Grocery> addGrocery(@RequestBody Grocery grocery) {
        try {
            Grocery _grocery = service.save(new Grocery(
                            grocery.getName(), grocery.getQuantity(), grocery.getNotes(), false));
            return new ResponseEntity<>(_grocery, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/grocery/{id}")
    public ResponseEntity<Grocery> editGrocery(@PathVariable("id") long id, @RequestBody Grocery grocery) {
        Optional<Grocery> groceryData = Optional.ofNullable(service.getById(id));

        if (groceryData.isPresent()) {
            Grocery _grocery = groceryData.get();
            _grocery.setName(grocery.getName());
            _grocery.setQuantity(grocery.getQuantity());
            _grocery.setNotes(grocery.getNotes());
            _grocery.setPurchased(grocery.isPurchased());
            return new ResponseEntity<>(service.save(_grocery), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/grocery/{id}")
    public ResponseEntity<HttpStatus> deleteGrocery(@PathVariable("id") long id) {
        try {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("/grocery")
    public ResponseEntity<HttpStatus> deleteGroceryList() {
        try {
            service.purge();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
