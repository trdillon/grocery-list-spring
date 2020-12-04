package com.itsdits.grocerylist.controller;

import com.itsdits.grocerylist.model.Item;
import com.itsdits.grocerylist.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
@PreAuthorize("hasAuthority('SCOPE_profile')")
public class ItemController {

    private final Logger log = LoggerFactory.getLogger(GroceryController.class);
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/item")
    List<Item> getUserGroceryList(Principal principal) {
        return itemService.getUserGroceryList(principal.getName());
    }

    @PostMapping("/item")
    ResponseEntity<Item> createItem(@Valid @RequestBody Item item) throws URISyntaxException {
        log.info("Request to create grocery item: {}", item);
        Item result = itemService.save(item);
        return ResponseEntity.created(new URI("/api/grocery/" + result.getId())).body(result);
    }

    @PutMapping("/item/{id}")
    ResponseEntity<Item> updateItem(@Valid @RequestBody Item item) {
        log.info("Request to update grocery item: {}", item);
        Item result = itemService.save(item);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/item/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        log.info("Request to delete grocery item: {}", id);
        itemService.delete(id);
        return ResponseEntity.ok().build();
    }
}
