package com.swiggy.catalog.controller;

import com.swiggy.catalog.dto.MenuItemRequest;
import com.swiggy.catalog.dto.RestaurantRequest;
import com.swiggy.catalog.model.MenuItem;
import com.swiggy.catalog.model.Restaurant;
import com.swiggy.catalog.service.MenuItemService;
import com.swiggy.catalog.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/restaurants")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("")
    public ResponseEntity<Object> createRestaurant(@RequestBody RestaurantRequest restaurantRequest) {
        try {
            Restaurant restaurant = restaurantService.createRestaurant(restaurantRequest.name(), restaurantRequest.location());

            return ResponseEntity.status(HttpStatus.CREATED).body(restaurant);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
