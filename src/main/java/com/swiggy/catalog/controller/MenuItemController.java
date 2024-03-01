package com.swiggy.catalog.controller;

import com.swiggy.catalog.constant.ResponseMessages;
import com.swiggy.catalog.dto.MenuItemRequest;
import com.swiggy.catalog.exception.MenuItemAlreadyExistException;
import com.swiggy.catalog.model.MenuItem;
import com.swiggy.catalog.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/restaurants/{restaurantId}/menu-items")
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    @PostMapping("")
    public ResponseEntity<Object> createUser(@RequestBody MenuItemRequest menuItemRequest) {
        try {
            MenuItem menuItem = menuItemService.createMenuItem(
                    menuItemRequest.itemName(),
                    menuItemRequest.price(),
                    menuItemRequest.restaurantId());

            return ResponseEntity.status(HttpStatus.CREATED).body(menuItem);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
