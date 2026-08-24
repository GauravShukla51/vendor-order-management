package com.vendor.repository;

import com.vendor.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findByInventoryDate(LocalDate inventoryDate);

    Optional<Inventory> findByVendorIdAndVegetableIdAndInventoryDate(
            Long vendorId,
            Long vegetableId,
            LocalDate inventoryDate
    );

    Optional<Inventory> findFirstByVegetableIdAndInventoryDateOrderByIdAsc(
            Long vegetableId,
            LocalDate inventoryDate
    );
}
