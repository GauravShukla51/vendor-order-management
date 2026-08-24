package com.vendor.service;

import com.vendor.entity.Restaurant;
import com.vendor.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    // Add Restaurant
    public Restaurant addRestaurant(Restaurant restaurant) {

        if (restaurantRepository.findByPhone(restaurant.getPhone()).isPresent()) {
            throw new RuntimeException("Restaurant with this phone already exists");
        }

        if (restaurant.getEmail() != null &&
                restaurantRepository.findByEmail(restaurant.getEmail()).isPresent()) {

            throw new RuntimeException("Restaurant with this email already exists");
        }

        restaurant.setActive(true);

        return restaurantRepository.save(restaurant);
    }

    // View All Restaurants
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    // View Restaurant By ID
    public Restaurant getRestaurantById(Long id) {

        return restaurantRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Restaurant not found with id: " + id));
    }

    // Update Restaurant
    public Restaurant updateRestaurant(Long id, Restaurant updatedRestaurant) {

        Restaurant existingRestaurant = getRestaurantById(id);

        existingRestaurant.setName(updatedRestaurant.getName());
        existingRestaurant.setPhone(updatedRestaurant.getPhone());
        existingRestaurant.setEmail(updatedRestaurant.getEmail());
        existingRestaurant.setAddress(updatedRestaurant.getAddress());

        return restaurantRepository.save(existingRestaurant);
    }

    // Activate Restaurant
    public Restaurant activateRestaurant(Long id) {

        Restaurant restaurant = getRestaurantById(id);

        restaurant.setActive(true);

        return restaurantRepository.save(restaurant);
    }

    // Deactivate Restaurant
    public Restaurant deactivateRestaurant(Long id) {

        Restaurant restaurant = getRestaurantById(id);

        restaurant.setActive(false);

        return restaurantRepository.save(restaurant);
    }
}