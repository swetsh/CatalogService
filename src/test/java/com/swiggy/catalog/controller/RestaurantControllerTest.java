package com.swiggy.catalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiggy.catalog.dto.RestaurantRequest;
import com.swiggy.catalog.model.Restaurant;
import com.swiggy.catalog.service.RestaurantService;
import com.swiggy.catalog.utils.Location;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void testCreateRestaurant_Success() throws Exception {
        RestaurantRequest restaurantRequest = new RestaurantRequest("test",  new Location());

        when(restaurantService.createRestaurant(anyString(), any(Location.class))).thenReturn(new Restaurant());

        mockMvc.perform(post("/api/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(restaurantRequest)))
                .andExpect(status().isCreated());

        verify(restaurantService, times(1)).createRestaurant(anyString(), any(Location.class));
    }


    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}