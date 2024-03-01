package com.swiggy.catalog.service;

import com.swiggy.catalog.exception.RestaurantNotFoundException;
import com.swiggy.catalog.model.Restaurant;
import com.swiggy.catalog.repository.RestaurantRepository;
import com.swiggy.catalog.utils.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    public Restaurant createRestaurant(String name, Location location) {
        Restaurant restaurant = new Restaurant(name, location);

        return restaurantRepository.save(restaurant);
    }

    public Restaurant getRestaurantWithID(int id) {
        return restaurantRepository.findById(id).orElseThrow(RestaurantNotFoundException::new);
    }
}
