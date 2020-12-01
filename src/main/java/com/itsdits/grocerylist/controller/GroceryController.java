package com.itsdits.grocerylist.controller;

import com.itsdits.grocerylist.exception.ResourceNotFoundException;
import com.itsdits.grocerylist.model.Grocery;
import com.itsdits.grocerylist.service.GroceryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class GroceryController {

    private final Logger log = LoggerFactory.getLogger(GroceryController.class);
    private final GroceryService service;

    public GroceryController(GroceryService service) {
        this.service = service;
    }

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
    public ResponseEntity<List<Grocery>> getNotPurchased(@RequestParam(required = false) String name) {
        List<Grocery> groceries = new ArrayList<>();

        if (name == null) {
            groceries.addAll(service.getNotPurchased());
        } else {
            groceries.addAll(service.getByName(name));
        }

        if (groceries.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(groceries, HttpStatus.OK);
    }

    @GetMapping("/grocery/purchased")
    public ResponseEntity<List<Grocery>> getPurchased(@RequestParam(required = false) String name) {
        List<Grocery> groceries = new ArrayList<>();

        if (name == null) {
            groceries.addAll(service.getPurchased());
        } else {
            groceries.addAll(service.getByName(name));
        }

        if (groceries.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(groceries, HttpStatus.OK);
    }

    @GetMapping("/grocery/favs")
    public ResponseEntity<List<Grocery>> getFavs(@RequestParam(required = false) String name) {
        List<Grocery> groceries = new ArrayList<>();

        if (name == null) {
            groceries.addAll(service.getFavorites());
        } else {
            groceries.addAll(service.getByName(name));
        }

        if (groceries.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(groceries, HttpStatus.OK);
    }

    @PostMapping("/grocery")
    public ResponseEntity<Grocery> addGrocery(@RequestBody Grocery grocery) {
        Grocery _grocery = service.save(new Grocery(
                grocery.getName(), grocery.getQuantity(), grocery.getPrice(),
                grocery.getNotes(), false, false));
        return new ResponseEntity<>(_grocery, HttpStatus.CREATED);
    }

    @PutMapping("/grocery/{id}")
    public ResponseEntity<Grocery> editGrocery(@PathVariable("id") long id, @RequestBody Grocery grocery) {
        Optional<Grocery> groceryData = Optional.ofNullable(service.getById(id));

        if (groceryData.isPresent()) {
            Grocery _grocery = groceryData.get();
            _grocery.setName(grocery.getName());
            _grocery.setQuantity(grocery.getQuantity());
            _grocery.setPrice(grocery.getPrice());
            _grocery.setNotes(grocery.getNotes());
            _grocery.setPurchased(grocery.isPurchased());
            _grocery.setFavorite(grocery.isFavorite());
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
