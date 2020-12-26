package com.itsdits.grocerylist.controller;

import com.itsdits.grocerylist.model.Item;
import com.itsdits.grocerylist.model.User;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

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
            String userId = details.get("sub").toString();
            Page<Item> itemPage = itemService.getItems(userId, pageRequest);
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

    @PostMapping("/add")
    ResponseEntity<Item> createItem(@Valid @RequestBody Item item,
                                    @AuthenticationPrincipal OAuth2User principal) throws URISyntaxException {
        log.info("Request to create item: {}", item);
        Map<String, Object> details = principal.getAttributes();
        String userId = details.get("sub").toString();

        // check to see if user already exists else create a new one
        Optional<User> user = userService.getUser(userId);
        item.setUser(user.orElse(new User(userId,
                details.get("name").toString(), details.get("email").toString())));

        Item result = itemService.save(item);
        return ResponseEntity.created(new URI("/api/item/" + result.getId())).body(result);
    }

    @PutMapping("/{id}")
    ResponseEntity<Item> updateItem(@Valid @RequestBody Item item) {
        log.info("Request to update item: {}", item);
        Item result = itemService.save(item);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        log.info("Request to delete item: {}", id);
        itemService.delete(id);
        return ResponseEntity.ok().build();
    }
}
