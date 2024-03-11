package com.swiggy.catalog.client;

import com.swiggy.catalog.model.User;
import com.swiggy.catalog.utils.Location;
import org.springframework.web.client.RestTemplate;

public class UserClient {

    private final RestTemplate restTemplate;

    public UserClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public User getUserByUserName() {
        return new User("Admin", "password", new Location());
    }
}
