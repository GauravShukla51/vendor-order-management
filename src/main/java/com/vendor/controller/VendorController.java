package com.vendor.controller;

import com.vendor.entity.Vendor;
import com.vendor.service.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    // Add Vendor
    @PostMapping
    public ResponseEntity<Vendor> addVendor(@RequestBody Vendor vendor) {
        return new ResponseEntity<>(
                vendorService.addVendor(vendor),
                HttpStatus.CREATED
        );
    }

    // View All Vendors
    @GetMapping
    public ResponseEntity<List<Vendor>> getAllVendors() {
        return ResponseEntity.ok(vendorService.getAllVendors());
    }

    // View Vendor By ID
    @GetMapping("/{id}")
    public ResponseEntity<Vendor> getVendorById(@PathVariable Long id) {
        return ResponseEntity.ok(vendorService.getVendorById(id));
    }

    // Update Vendor
    @PutMapping("/{id}")
    public ResponseEntity<Vendor> updateVendor(
            @PathVariable Long id,
            @RequestBody Vendor vendor) {

        return ResponseEntity.ok(
                vendorService.updateVendor(id, vendor)
        );
    }

    // Activate Vendor
    @PatchMapping("/{id}/activate")
    public ResponseEntity<Vendor> activateVendor(@PathVariable Long id) {
        return ResponseEntity.ok(
                vendorService.activateVendor(id)
        );
    }

    // Deactivate Vendor
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Vendor> deactivateVendor(@PathVariable Long id) {
        return ResponseEntity.ok(
                vendorService.deactivateVendor(id)
        );
    }
}