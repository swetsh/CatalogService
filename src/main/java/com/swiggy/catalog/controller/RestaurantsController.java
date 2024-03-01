package com.swiggy.catalog.controller;

import com.swiggy.catalog.constant.ResponseMessages;
import com.swiggy.catalog.dto.RestaurantsRequest;
import com.swiggy.catalog.exception.RestaurantNotFoundException;
import com.swiggy.catalog.model.Restaurant;
import com.swiggy.catalog.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/restaurants")
public class RestaurantsController {
    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("")
    public ResponseEntity<Object> fetchALl() {
        try {
            List<Restaurant> restaurants = restaurantService.fetchALl();
            return ResponseEntity.status(HttpStatus.FOUND).body(restaurants);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<Object> fetchById(@PathVariable("restaurantId") int restaurantId) {
        try {
            Restaurant restaurant = restaurantService.getRestaurantWithID(restaurantId);
            return ResponseEntity.status(HttpStatus.FOUND).body(restaurant);
        } catch (RestaurantNotFoundException restaurantNotFoundException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseMessages.RESTAURANT_DOES_NOT_EXIST_WITH_ID);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<Object> create(@RequestBody RestaurantsRequest restaurantsRequest) {
        try {
            Restaurant restaurant = restaurantService.create(restaurantsRequest.name(), restaurantsRequest.location());

            return ResponseEntity.status(HttpStatus.CREATED).body(restaurant);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
