package com.itsdits.grocerylist.controller;

import com.itsdits.grocerylist.model.Item;
import com.itsdits.grocerylist.service.ItemService;
import com.itsdits.grocerylist.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/item")
@PreAuthorize("hasAuthority('SCOPE_profile')")
public class ItemController {

    private final Logger log = LoggerFactory.getLogger(ItemController.class);
    private final ItemService itemService;
    private final UserService userService;

    public ItemController(ItemService itemService, UserService userService) {
        this.itemService = itemService;
        this.userService = userService;
    }

    @GetMapping(produces = "application/json; charset=UTF-8")
    ResponseEntity<Map<String, Object>> getItems(
            @AuthenticationPrincipal OAuth2User principal,
            @RequestParam(required = false) final String name, //FIXME: search doesn't work, no QBE impl
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
            Map<String, Object> details = principal.getAttributes();
            String user = details.get("sub").toString();
            Page<Item> itemPage = itemService.getItems(user, pageRequest);
            List<Item> itemList = itemPage.getContent();

            if(itemList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            // build the response
            Map<String, Object> response = new HashMap<>();
            response.put("items", itemList);
            response.put("currentPage", itemPage.getNumber());
            response.put("totalItems", itemPage.getTotalElements());
            response.put("totalPages", itemPage.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
