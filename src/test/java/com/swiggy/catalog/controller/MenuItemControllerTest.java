package com.swiggy.catalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiggy.catalog.constant.ResponseMessages;
import com.swiggy.catalog.dto.MenuItemRequest;
import com.swiggy.catalog.exception.RestaurantNotFoundException;
import com.swiggy.catalog.model.MenuItem;
import com.swiggy.catalog.service.MenuItemService;
import com.swiggy.catalog.utils.Money;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.swiggy.catalog.utils.Currency.INR;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MenuItemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MenuItemService menuItemService;

    @Test
    public void testCreateMenuItem_Success() throws Exception {
        MenuItemRequest menuItemRequest = new MenuItemRequest("test", new Money(10, INR));

        when(menuItemService.createMenuItem(anyString(), any(Money.class), anyInt())).thenReturn(new MenuItem());

        mockMvc.perform(post("/api/v1/restaurants/1/menu-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(menuItemRequest)))
                .andExpect(status().isCreated());

        verify(menuItemService, times(1)).createMenuItem(anyString(), any(Money.class), eq(1));
        verify(menuItemService, never()).createMenuItem(anyString(), any(Money.class), eq(2));
    }

    @Test
    public void testCreateMenuItem_RestaurantNotFound() throws Exception {
        MenuItemRequest menuItemRequest = new MenuItemRequest("test", new Money(10, INR));

        when(menuItemService.createMenuItem(anyString(), any(Money.class), anyInt())).thenThrow(RestaurantNotFoundException.class);

        mockMvc.perform(post("/api/v1/restaurants/1/menu-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(menuItemRequest)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().string(ResponseMessages.RESTAURANT_DOES_NOT_EXIST_WITH_ID));

    }


    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}