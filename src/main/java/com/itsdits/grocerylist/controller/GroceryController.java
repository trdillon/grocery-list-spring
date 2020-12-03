package com.itsdits.grocerylist.controller;

import com.itsdits.grocerylist.exception.ResourceNotFoundException;
import com.itsdits.grocerylist.model.Grocery;
import com.itsdits.grocerylist.service.GroceryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
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

    @GetMapping("/grocery")
    @PreAuthorize("hasAuthority('SCOPE_profile')")
    Collection<Grocery> groceryList(Principal principal) {
        return groceryService.getByUserId(principal.getName());
    }

    @GetMapping("/grocery/{id}")
    ResponseEntity<?> getGrocery(@PathVariable Long id) {
        Optional<Grocery> grocery = Optional.ofNullable(groceryService.getById(id));
        return grocery.map(response -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new ResourceNotFoundException("No grocery with the ID " + id + " was found."));
    }

    @PostMapping("/grocery")
    @PreAuthorize("hasAuthority('SCOPE_profile')")
    ResponseEntity<Grocery> createGrocery(@Valid @RequestBody Grocery grocery) throws URISyntaxException {
        log.info("Request to create grocery: {}", grocery);
        Grocery result = groceryService.save(grocery);
        return ResponseEntity.created(new URI("/api/grocery/" + result.getId())).body(result);
    }

    @PutMapping("/grocery/{id}")
    @PreAuthorize("hasAuthority('SCOPE_profile')")
    ResponseEntity<Grocery> updateGrocery(@Valid @RequestBody Grocery grocery) {
        log.info("Request to update grocery: {}", grocery);
        Grocery result = groceryService.save(grocery);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/grocery/{id}")
    @PreAuthorize("hasAuthority('SCOPE_profile')")
    public ResponseEntity<?> deleteGrocery(@PathVariable Long id) {
        log.info("Request to delete grocery: {}", id);
        groceryService.delete(id);
        return ResponseEntity.ok().build();
    }
}
