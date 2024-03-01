package com.swiggy.catalog.service;

import com.swiggy.catalog.exception.MenuItemAlreadyExistException;
import com.swiggy.catalog.model.MenuItem;
import com.swiggy.catalog.model.Restaurant;
import com.swiggy.catalog.repository.MenuItemRepository;
import com.swiggy.catalog.utils.Currency;
import com.swiggy.catalog.utils.Money;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static com.swiggy.catalog.utils.Currency.INR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class MenuItemServiceTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @InjectMocks
    private MenuItemService menuItemService;

    @Test
    public void testCreateMenuItem_Success() {
        String itemName = "Biryani";
        Money price = new Money(99, INR);
        Integer restaurantId = 1;

        MenuItem menuItem = new MenuItem(itemName, price, new Restaurant());


        when(menuItemRepository.save(any(MenuItem.class))).thenReturn(menuItem);

        MenuItem createdMenuItem = menuItemService.createMenuItem(itemName, price, restaurantId);

        assertNotNull(createdMenuItem);
        verify(menuItemRepository, times(1)).save(eq(createdMenuItem));
    }
}