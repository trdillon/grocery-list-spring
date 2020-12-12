package com.itsdits.grocerylist.controller;

import com.itsdits.grocerylist.exception.ResourceNotFoundException;
import com.itsdits.grocerylist.model.Grocery;
import com.itsdits.grocerylist.model.dto.GrocerySearchDto;
import com.itsdits.grocerylist.service.GroceryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/groceries")
public class GroceryController {

    private final GroceryService groceryService;

    @GetMapping(produces = "application/json; charset=UTF-8")
    ResponseEntity<Map<String, Object>> getGroceries(
            @RequestParam(required = false) final String name,
            @RequestParam(required = false) final String group,
            @RequestParam(required = false) final String subGroup,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "name,asc") String[] sort) {
        try {
            // first build the PageRequest object with sort properties and directions
            List<Order> orders = new ArrayList<>();
            if (sort[0].contains(",")) {
                // sort more than 2 fields
                // sortOrder=[field, direction]
                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Order(Direction.fromString(_sort[1]), _sort[0]));
                }
            } else {
                // sort=[field, direction]
                orders.add(new Order(Direction.fromString(sort[1]), sort[0]));
            }
            Pageable pageRequest = PageRequest.of(page, size, Sort.by(orders));

            // now build the query
            GrocerySearchDto grocerySearchDto = new GrocerySearchDto(name, group, subGroup);
            Page<Grocery> groceryPage = groceryService.getAll(grocerySearchDto, pageRequest);
            List<Grocery> groceryList = groceryPage.getContent();

            if(groceryList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            // build the response
            Map<String, Object> response = new HashMap<>();
            response.put("groceries", groceryList);
            response.put("currentPage", groceryPage.getNumber());
            response.put("totalItems", groceryPage.getTotalElements());
            response.put("totalPages", groceryPage.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace(); //FIXME: remove after debugging
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getGrocery(@PathVariable Long id) {
        Optional<Grocery> grocery = Optional.ofNullable(groceryService.getById(id));
        return grocery.map(response -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new ResourceNotFoundException("No grocery with the ID " + id + " was found."));
    }
}
