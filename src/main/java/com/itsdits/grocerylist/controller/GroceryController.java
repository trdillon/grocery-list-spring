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

/**
 * GroceryController.java - This class provides a REST controller for the grocery API endpoints.
 * Utilizes an instance of {@link GroceryService} to handle the logic of the API calls.
 *
 * @author Tim Dillon
 * @version 1.0
 */
@RestController
@RequestMapping("/api")
public class GroceryController {

    @Autowired
    private GroceryService service;

    /**
     * GET call gets a list of all Grocery items, or any matching the search criteria if provided.
     *
     * @param name Optional String param for searching
     * @return List of all Grocery objects, List of Grocery objects containing name param, or
     *         HttpStatus.NO_CONTENT if List is empty
     */
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

    /**
     * GET call gets a single Grocery item by its id value.
     *
     * @param id long param representing the id of the desired Grocery item
     * @return Grocery object with matching id
     * @throws ResourceNotFoundException if no matching id was found
     */
    @GetMapping("/grocery/{id}")
    public ResponseEntity<Grocery> getGroceryById(@PathVariable("id") long id) {
        Optional<Grocery> grocery = Optional.ofNullable(service.getById(id));

        return grocery.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException("No grocery with the ID " + id + " was found."));
    }

    /**
     * GET call gets a List of Grocery items that need to be purchased.
     *
     * @return List of Grocery objects where {@code purchased = false} or
     *         HttpStatus.NO_CONTENT if no items match criteria
     */
    @GetMapping("/grocery/need")
    public ResponseEntity<List<Grocery>> getNotPurchased() {
        List<Grocery> groceries = service.getNotPurchased();

        if (groceries.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(groceries, HttpStatus.OK);
        }
    }

    /**
     * GET call gets a List of Grocery items that have already been purchased.
     *
     * @return List of Grocery objects where {@code purchased = true} or
     *         HttpStatus.NO_CONTENT if no items match criteria
     */
    @GetMapping("/grocery/purchased")
    public ResponseEntity<List<Grocery>> getPurchased() {
        List<Grocery> groceries = service.getPurchased();

        if (groceries.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(groceries, HttpStatus.OK);
        }
    }

    /**
     * POST call creates a new Grocery item.
     *
     * @param grocery Grocery object
     * @return new Grocery object _grocery
     */
    @PostMapping("/grocery")
    public ResponseEntity<Grocery> addGrocery(@RequestBody Grocery grocery) {
        Grocery _grocery = service.save(new Grocery(
                grocery.getName(), grocery.getQuantity(), grocery.getNotes(), false));
        return new ResponseEntity<>(_grocery, HttpStatus.CREATED);
    }

    /**
     * PUT call updates Grocery item based on id param.
     *
     * @param id long param representing the id of the desired Grocery item
     * @param grocery Grocery object holding data to be saved to the existing Grocery item
     * @return updated Grocery object _grocery
     * @throws ResourceNotFoundException if no matching id was found
     */
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

    /**
     * DELETE call deletes a single Grocery item based on id param.
     *
     * @param id long param representing the id of the desired Grocery item
     * @return HttpStatus.NO_CONTENT
     */
    @DeleteMapping("/grocery/{id}")
    public ResponseEntity<HttpStatus> deleteGrocery(@PathVariable("id") long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * DELETE call deletes all Grocery items in the database.
     *
     * @return HttpStatus.NO_CONTENT
     */
    @DeleteMapping("/grocery")
    public ResponseEntity<HttpStatus> deleteGroceryList() {
        service.purge();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
