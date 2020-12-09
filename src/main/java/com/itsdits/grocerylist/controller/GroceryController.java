package com.itsdits.grocerylist.controller;

import com.itsdits.grocerylist.exception.ResourceNotFoundException;
import com.itsdits.grocerylist.model.Grocery;
import com.itsdits.grocerylist.service.GroceryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class GroceryController {

    private final GroceryService groceryService;

    public GroceryController(GroceryService groceryService) {
        this.groceryService = groceryService;
    }

    @GetMapping("/groceries")
    ResponseEntity<Map<String, Object>> getGroceries(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "name,asc") String[] sort) {
        try {
            List<Order> orders = new ArrayList<>();

            if (sort[0].contains(",")) {
                // will sort more than 2 fields
                // sortOrder=[field, direction]
                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Order(Direction.fromString(_sort[1]), _sort[0]));
                }
            } else {
                // sort=[field, direction]
                orders.add(new Order(Direction.fromString(sort[1]), sort[0]));
            }

            Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

            Page<Grocery> pageGroceries;
            if(name == null)
                pageGroceries = groceryService.getAll(pagingSort);
            else
                pageGroceries = groceryService.getByName(name, pagingSort);

            List<Grocery> groceries = pageGroceries.getContent();

            if(groceries.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("groceries", groceries);
            response.put("currentPage", pageGroceries.getNumber());
            response.put("totalItems", pageGroceries.getTotalElements());
            response.put("totalPages", pageGroceries.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/grocery/{id}")
    ResponseEntity<?> getGrocery(@PathVariable Long id) {
        Optional<Grocery> grocery = Optional.ofNullable(groceryService.getById(id));
        return grocery.map(response -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new ResourceNotFoundException("No grocery with the ID " + id + " was found."));
    }
}
