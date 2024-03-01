package com.swiggy.catalog.service;

import com.swiggy.catalog.exception.RestaurantNotFoundException;
import com.swiggy.catalog.model.Restaurant;
import com.swiggy.catalog.repository.RestaurantRepository;
import com.swiggy.catalog.utils.Location;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RestaurantServiceTest {
    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantService restaurantService;

    @Test
    public void testCreateRestaurant_Success() {
        String name = "Dhaba";
        Location location = new Location("40.8736, -53.7564");

        Restaurant restaurant = new Restaurant(name, location);


        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);

        Restaurant createdRestaurant = restaurantService.createRestaurant(name, location);

        assertNotNull(createdRestaurant);
        verify(restaurantRepository, times(1)).save(eq(createdRestaurant));
    }

    @Test
    public void testFindRestaurant_Success() {
        String name = "Dhaba";
        Location location = new Location("40.8736, -53.7564");

        Restaurant restaurant = new Restaurant(name, location);

        when(restaurantRepository.findById(anyInt())).thenReturn(Optional.of(restaurant));

        Restaurant createdRestaurant = restaurantService.getRestaurantWithID(1);

        assertNotNull(createdRestaurant);
        verify(restaurantRepository, times(1)).findById(anyInt());
    }

    @Test
    public void testFindRestaurant_NotFound() {
        String name = "Dhaba";
        Location location = new Location("40.8736, -53.7564");

        Restaurant restaurant = new Restaurant(name, location);

        when(restaurantRepository.findById(anyInt())).thenThrow(RestaurantNotFoundException.class);

        assertThrows(RestaurantNotFoundException.class, () -> restaurantService.getRestaurantWithID(1));
    }
}
