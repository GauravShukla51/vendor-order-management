package com.vendor.service;

import com.vendor.entity.Inventory;
import com.vendor.entity.Vegetable;
import com.vendor.entity.Vendor;
import com.vendor.repository.InventoryRepository;
import com.vendor.repository.VegetableRepository;
import com.vendor.repository.VendorRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    private final VendorRepository vendorRepository;

    private final VegetableRepository vegetableRepository;

    public InventoryService(
            InventoryRepository inventoryRepository,
            VendorRepository vendorRepository,
            VegetableRepository vegetableRepository) {

        this.inventoryRepository = inventoryRepository;
        this.vendorRepository = vendorRepository;
        this.vegetableRepository = vegetableRepository;
    }

    // Add Inventory
    public Inventory addInventory(
            Long vendorId,
            String vegetableName,
            Inventory inventory) {

        Vendor vendor = vendorRepository.findById(vendorId)

                .orElseThrow(() ->
                        new RuntimeException(
                                "Vendor not found with id: " + vendorId));

        if (!vendor.isActive()) {

            throw new RuntimeException("Vendor is inactive");
        }

        Vegetable vegetable = vegetableRepository
                .findByNameIgnoreCase(vegetableName)

                .orElseGet(() -> {

                    Vegetable newVegetable = new Vegetable();

                    newVegetable.setName(vegetableName);

                    return vegetableRepository.save(newVegetable);
                });

        LocalDate today = LocalDate.now();

        if (inventoryRepository
                .findByVendorIdAndVegetableIdAndInventoryDate(
                        vendorId,
                        vegetable.getId(),
                        today)
                .isPresent()) {

            throw new RuntimeException(
                    "Inventory already exists for this vegetable today");
        }

        inventory.setVendor(vendor);

        inventory.setVegetable(vegetable);

        inventory.setInventoryDate(today);

        return inventoryRepository.save(inventory);
    }

    // Get Today's Inventory
    public List<Inventory> getTodayInventory() {

        return inventoryRepository.findByInventoryDate(
                LocalDate.now());
    }

    // Get Inventory By ID
    public Inventory getInventoryById(Long id) {

        return inventoryRepository.findById(id)

                .orElseThrow(() ->
                        new RuntimeException(
                                "Inventory not found with id: " + id));
    }

    // Update Inventory
    public Inventory updateInventory(
            Long id,
            Inventory updatedInventory) {

        // 1. Find existing inventory
        Inventory inventory = inventoryRepository.findById(id)

                .orElseThrow(() ->
                        new RuntimeException(
                                "Inventory not found with id: " + id));

        // 2. Validate quantity
        if (updatedInventory.getQuantity() == null ||
                updatedInventory.getQuantity()
                        .compareTo(BigDecimal.ZERO) <= 0) {

            throw new RuntimeException(
                    "Quantity must be greater than zero");
        }

        // 3. Validate unit
        if (updatedInventory.getUnit() == null ||
                updatedInventory.getUnit().isBlank()) {

            throw new RuntimeException(
                    "Unit is required");
        }

        // 4. Validate price
        if (updatedInventory.getPrice() == null ||
                updatedInventory.getPrice()
                        .compareTo(BigDecimal.ZERO) <= 0) {

            throw new RuntimeException(
                    "Price must be greater than zero");
        }

        // 5. Update only editable fields
        inventory.setQuantity(
                updatedInventory.getQuantity());

        inventory.setUnit(
                updatedInventory.getUnit());

        inventory.setPrice(
                updatedInventory.getPrice());

        // 6. Save updated inventory
        return inventoryRepository.save(inventory);
    }
}