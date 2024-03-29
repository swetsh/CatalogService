package com.swiggy.catalog.service;

import com.swiggy.catalog.exception.LocationAlreadyExistException;
import com.swiggy.catalog.exception.RestaurantNotFoundException;
import com.swiggy.catalog.model.Restaurant;
import com.swiggy.catalog.repository.RestaurantRepository;
import com.swiggy.catalog.utils.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    public Restaurant create(String name, Location location) {
        try {
            Restaurant restaurant = new Restaurant(name, location);
            return restaurantRepository.save(restaurant);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new LocationAlreadyExistException();
        }
    }

    public Restaurant getRestaurantWithID(int id) {
        return restaurantRepository.findById(id).orElseThrow(RestaurantNotFoundException::new);
    }

    public List<Restaurant> fetchALl() {
        return restaurantRepository.findAll();
    }
}
