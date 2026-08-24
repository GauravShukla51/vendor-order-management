package com.vendor.controller;

import com.vendor.entity.Restaurant;
import com.vendor.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    // Add Restaurant
    @PostMapping
    public ResponseEntity<Restaurant> addRestaurant(
            @RequestBody Restaurant restaurant) {

        return new ResponseEntity<>(
                restaurantService.addRestaurant(restaurant),
                HttpStatus.CREATED
        );
    }

    // View All Restaurants
    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {

        return ResponseEntity.ok(
                restaurantService.getAllRestaurants()
        );
    }

    // View Restaurant By ID
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                restaurantService.getRestaurantById(id)
        );
    }

    // Update Restaurant
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(
            @PathVariable Long id,
            @RequestBody Restaurant restaurant) {

        return ResponseEntity.ok(
                restaurantService.updateRestaurant(id, restaurant)
        );
    }

    // Activate Restaurant
    @PatchMapping("/{id}/activate")
    public ResponseEntity<Restaurant> activateRestaurant(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                restaurantService.activateRestaurant(id)
        );
    }

    // Deactivate Restaurant
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Restaurant> deactivateRestaurant(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                restaurantService.deactivateRestaurant(id)
        );
    }

}