package com.vendor.controller;

import com.vendor.entity.Inventory;
import com.vendor.service.InventoryService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {

        this.inventoryService = inventoryService;
    }

    // Add Inventory
    @PostMapping("/vendor/{vendorId}")
    public ResponseEntity<Inventory> addInventory(

            @PathVariable Long vendorId,

            @RequestParam String vegetableName,

            @RequestBody Inventory inventory) {

        return new ResponseEntity<>(

                inventoryService.addInventory(
                        vendorId,
                        vegetableName,
                        inventory),

                HttpStatus.CREATED
        );
    }

    // Get Today's Inventory
    @GetMapping("/today")
    public ResponseEntity<List<Inventory>> getTodayInventory() {

        return ResponseEntity.ok(

                inventoryService.getTodayInventory()

        );
    }

    // Get Inventory By ID
    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(

            @PathVariable Long id) {

        return ResponseEntity.ok(

                inventoryService.getInventoryById(id)

        );
    }

    // Update Inventory
    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateInventory(

            @PathVariable Long id,

            @RequestBody Inventory inventory) {

        return ResponseEntity.ok(

                inventoryService.updateInventory(
                        id,
                        inventory)

        );
    }
}