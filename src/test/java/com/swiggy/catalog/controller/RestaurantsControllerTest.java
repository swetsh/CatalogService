package com.swiggy.catalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiggy.catalog.constant.ResponseMessages;
import com.swiggy.catalog.dto.RestaurantsRequest;
import com.swiggy.catalog.exception.LocationAlreadyExistException;
import com.swiggy.catalog.exception.RestaurantNotFoundException;
import com.swiggy.catalog.model.Restaurant;
import com.swiggy.catalog.service.RestaurantService;
import com.swiggy.catalog.utils.Location;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RestaurantsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    @WithMockUser
    public void testCreateRestaurant_Success() throws Exception {
        RestaurantsRequest restaurantsRequest = new RestaurantsRequest("test", new Location());

        when(restaurantService.create(anyString(), any(Location.class))).thenReturn(new Restaurant());

        mockMvc.perform(post("/api/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(restaurantsRequest)))
                .andExpect(status().isCreated());

        verify(restaurantService, times(1)).create(anyString(), any(Location.class));
    }

    @Test
    @WithMockUser
    public void testCreateRestaurant_LocationExists() throws Exception {
        RestaurantsRequest restaurantsRequest = new RestaurantsRequest("test", new Location());

        when(restaurantService.create(anyString(), any(Location.class))).thenThrow(LocationAlreadyExistException.class);

        mockMvc.perform(post("/api/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(restaurantsRequest)))
                .andExpect(status().isConflict())
                .andExpect((MockMvcResultMatchers.content().string(ResponseMessages.RESTAURANT_EXIST_AT_LOCATION)));


        verify(restaurantService, times(1)).create(anyString(), any(Location.class));
    }

    @Test
    @WithMockUser
    public void testGetRestaurantWithId_Success() throws Exception {
        Restaurant restaurant = new Restaurant("one", new Location());
        Restaurant secondRestaurant = new Restaurant("two", new Location());


        when(restaurantService.getRestaurantWithID(2)).thenReturn(secondRestaurant);

        mockMvc.perform(get("/api/v1/restaurants/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.name").value("two"));


        verify(restaurantService, times(1)).getRestaurantWithID(2);
        verify(restaurantService, never()).getRestaurantWithID(1);
    }

    @Test
    @WithMockUser
    public void testGetRestaurantWithId_NotFound() throws Exception {
        when(restaurantService.getRestaurantWithID(2)).thenThrow(RestaurantNotFoundException.class);

        mockMvc.perform(get("/api/v1/restaurants/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(ResponseMessages.RESTAURANT_DOES_NOT_EXIST_WITH_ID));
    }

    @Test
    @WithMockUser
    public void testGetAllRestaurant_Success() throws Exception {
        Restaurant restaurant = new Restaurant("one", new Location());
        Restaurant secondRestaurant = new Restaurant("two", new Location());


        when(restaurantService.fetchALl()).thenReturn(Arrays.asList(restaurant, secondRestaurant));

        mockMvc.perform(get("/api/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("one"))
                .andExpect(jsonPath("$[1].name").value("two"));


        verify(restaurantService, times(1)).fetchALl();
    }


    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}