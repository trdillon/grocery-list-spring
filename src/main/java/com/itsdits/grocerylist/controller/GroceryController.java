package com.itsdits.grocerylist.controller;

import com.itsdits.grocerylist.model.Grocery;
import com.itsdits.grocerylist.service.GroceryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
}
