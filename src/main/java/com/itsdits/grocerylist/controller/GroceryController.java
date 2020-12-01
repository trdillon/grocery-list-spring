package com.itsdits.grocerylist.controller;

import com.itsdits.grocerylist.exception.ResourceNotFoundException;
import com.itsdits.grocerylist.model.Grocery;
import com.itsdits.grocerylist.service.GroceryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
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

    @GetMapping("/groceries")
    Collection<Grocery> groceries() {
        return service.getAll();
    }

    @GetMapping("/grocery/{id}")
    ResponseEntity<?> getGrocery(@PathVariable Long id) {
        Optional<Grocery> grocery = Optional.ofNullable(service.getById(id));
        return grocery.map(response -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new ResourceNotFoundException("No grocery with the ID " + id + " was found."));
    }

    @PostMapping("/grocery")
    ResponseEntity<Grocery> createGrocery(@Valid @RequestBody Grocery grocery) throws URISyntaxException {
        log.info("Request to create grocery: {}", grocery);
        Grocery result = service.save(grocery);
        return ResponseEntity.created(new URI("/api/grocery/" + result.getId())).body(result);
    }

    @PutMapping("/grocery/{id}")
    ResponseEntity<Grocery> updateGrocery(@Valid @RequestBody Grocery grocery) {
        log.info("Request to update grocery: {}", grocery);
        Grocery result = service.save(grocery);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/grocery/{id}")
    public ResponseEntity<?> deleteGrocery(@PathVariable Long id) {
        log.info("Request to delete grocery: {}", id);
        service.delete(id);
        return ResponseEntity.ok().build();
    }

/*
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
 */
}
