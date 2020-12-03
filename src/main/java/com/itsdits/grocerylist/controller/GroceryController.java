package com.itsdits.grocerylist.controller;

import com.itsdits.grocerylist.exception.ResourceNotFoundException;
import com.itsdits.grocerylist.model.Grocery;
import com.itsdits.grocerylist.model.User;
import com.itsdits.grocerylist.repository.UserRepository;
import com.itsdits.grocerylist.service.GroceryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class GroceryController {

    private final Logger log = LoggerFactory.getLogger(GroceryController.class);
    private final GroceryService groceryService;
    private final UserRepository userRepository;

    public GroceryController(GroceryService groceryService, UserRepository userRepository) {
        this.groceryService = groceryService;
        this.userRepository = userRepository;
    }

    @GetMapping("/groceries")
    Collection<Grocery> groceries() {
        return groceryService.getAll();
    }

    @GetMapping("/grocery")
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
    ResponseEntity<Grocery> createGrocery(@Valid @RequestBody Grocery grocery,
                                          @AuthenticationPrincipal OAuth2User principal) throws URISyntaxException {
        log.info("Request to create grocery: {}", grocery);
        Map<String, Object> data = principal.getAttributes();
        String userId = data.get("sub").toString();

        // check to see if user already exists
        Optional<User> user = userRepository.findById(userId);
        grocery.setUser(user.orElse(new User(userId,
                data.get("name").toString(), data.get("email").toString())));

        Grocery result = groceryService.save(grocery);
        return ResponseEntity.created(new URI("/api/grocery/" + result.getId())).body(result);
    }

    @PutMapping("/grocery/{id}")
    ResponseEntity<Grocery> updateGrocery(@Valid @RequestBody Grocery grocery) {
        log.info("Request to update grocery: {}", grocery);
        Grocery result = groceryService.save(grocery);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/grocery/{id}")
    public ResponseEntity<?> deleteGrocery(@PathVariable Long id) {
        log.info("Request to delete grocery: {}", id);
        groceryService.delete(id);
        return ResponseEntity.ok().build();
    }
}
