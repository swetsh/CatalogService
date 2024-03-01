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


@RestController
@RequestMapping("api/v1/restaurants/{restaurantId}/menu-items")
public class MenuItemsController {
    @Autowired
    private MenuItemService menuItemService;

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
