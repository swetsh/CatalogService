package com.swiggy.catalog.controller;

import com.swiggy.catalog.constant.ResponseMessages;
import com.swiggy.catalog.dto.MenuItemsRequest;
import com.swiggy.catalog.exception.RestaurantNotFoundException;
import com.swiggy.catalog.model.MenuItem;
import com.swiggy.catalog.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/restaurants/{restaurantId}/menu-items")
public class MenuItemsController {
    @Autowired
    private MenuItemService menuItemService;


    @GetMapping("")
    public ResponseEntity<Object> listAll(@PathVariable("restaurantId") int restaurantId) {
        try {
            List<MenuItem> menuItems = menuItemService.listAllByRestaurantId(restaurantId);
            return ResponseEntity.status(HttpStatus.FOUND).body(menuItems);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<Object> create(@PathVariable("restaurantId") int restaurantId , @RequestBody MenuItemsRequest menuItemsRequest) {
        try {
            MenuItem menuItem = menuItemService.create(
                    menuItemsRequest.itemName(),
                    menuItemsRequest.price(),
                    restaurantId);

            return ResponseEntity.status(HttpStatus.CREATED).body(menuItem);
        }
        catch (RestaurantNotFoundException restaurantNotFoundException){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResponseMessages.RESTAURANT_DOES_NOT_EXIST_WITH_ID);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }
}
