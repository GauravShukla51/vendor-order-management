package com.vendor.repository;

import com.vendor.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Optional<Restaurant> findByPhone(String phone);

    Optional<Restaurant> findByEmail(String email);
}
