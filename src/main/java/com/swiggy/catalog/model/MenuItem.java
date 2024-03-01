package com.swiggy.catalog.model;


import com.swiggy.catalog.utils.Money;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String itemName;

    @Embedded
    private Money price;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private Restaurant restaurant;

    public MenuItem(String itemName, Money price, Restaurant restaurant) {
        this.itemName = itemName;
        this.price = price;
        this.restaurant = restaurant;
    }
}

