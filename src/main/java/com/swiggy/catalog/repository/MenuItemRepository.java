package com.swiggy.catalog.repository;

import com.swiggy.catalog.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {
//    @Query("SELECT mi FROM MenuItem mi WHERE mi.restaurant.id = :restaurantId")
    List<MenuItem> findAllByRestaurantId(int restaurantId);

}
