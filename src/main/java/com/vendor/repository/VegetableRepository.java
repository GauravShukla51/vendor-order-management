package com.vendor.repository;

import com.vendor.entity.Vegetable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VegetableRepository extends JpaRepository<Vegetable, Long> {

    Optional<Vegetable> findByNameIgnoreCase(String name);
}