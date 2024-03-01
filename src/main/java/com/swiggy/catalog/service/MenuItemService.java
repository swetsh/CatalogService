package com.swiggy.catalog.service;

import com.swiggy.catalog.model.MenuItem;
import com.swiggy.catalog.model.Restaurant;
import com.swiggy.catalog.repository.MenuItemRepository;
import com.swiggy.catalog.utils.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuItemService {
    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RestaurantService restaurantService;

    public MenuItem create(String name, Money price, Integer restaurantId) {
        Restaurant restaurant = restaurantService.getRestaurantWithID(restaurantId);

        MenuItem menuItem = new MenuItem(name, price, restaurant);

        return menuItemRepository.save(menuItem);
    }
}
