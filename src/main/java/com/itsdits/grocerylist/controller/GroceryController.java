package com.itsdits.grocerylist.controller;

import com.itsdits.grocerylist.exception.ResourceNotFoundException;
import com.itsdits.grocerylist.model.Grocery;
import com.itsdits.grocerylist.service.GroceryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class GroceryController {

    @Autowired
    private GroceryService service;

    @GetMapping("/grocery")
    public ResponseEntity<List<Grocery>> getGroceryList(@RequestParam(required = false) String name) {
        List<Grocery> groceries = new ArrayList<>();

        if (name == null) {
            groceries.addAll(service.getAll());
        } else {
            groceries.addAll(service.getByName(name));
        }

        if (groceries.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(groceries, HttpStatus.OK);
    }

    @GetMapping("/grocery/{id}")
    public ResponseEntity<Grocery> getGroceryById(@PathVariable("id") long id) {
        Optional<Grocery> grocery = Optional.ofNullable(service.getById(id));

        return grocery.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException("No grocery with the ID " + id + " was found."));
    }

    @GetMapping("/grocery/need")
    public ResponseEntity<List<Grocery>> getNotPurchased() {
        List<Grocery> groceries = service.getNotPurchased();

        if (groceries.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(groceries, HttpStatus.OK);
        }
    }

    @GetMapping("/grocery/purchased")
    public ResponseEntity<List<Grocery>> getPurchased() {
        List<Grocery> groceries = service.getPurchased();

        if (groceries.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(groceries, HttpStatus.OK);
        }
    }

    @PostMapping("/grocery")
    public ResponseEntity<Grocery> addGrocery(@RequestBody Grocery grocery) {
        Grocery _grocery = service.save(new Grocery(
                grocery.getName(), grocery.getQuantity(), grocery.getNotes(), false));
        return new ResponseEntity<>(_grocery, HttpStatus.CREATED);
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
            throw new ResourceNotFoundException("No grocery with the ID " + id + " was found.");
        }
    }

    @DeleteMapping("/grocery/{id}")
    public ResponseEntity<HttpStatus> deleteGrocery(@PathVariable("id") long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/grocery")
    public ResponseEntity<HttpStatus> deleteGroceryList() {
        service.purge();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
