package com.vendor.service;

import com.vendor.entity.Vendor;
import com.vendor.repository.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorService {

    private final VendorRepository vendorRepository;

    public VendorService(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    // Add Vendor
    public Vendor addVendor(Vendor vendor) {

        if (vendorRepository.findByPhone(vendor.getPhone()).isPresent()) {
            throw new RuntimeException("Vendor with this phone already exists");
        }

        if (vendor.getEmail() != null &&
                vendorRepository.findByEmail(vendor.getEmail()).isPresent()) {
            throw new RuntimeException("Vendor with this email already exists");
        }

        vendor.setActive(true);

        return vendorRepository.save(vendor);
    }

    // View All Vendors
    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    // View Vendor By ID
    public Vendor getVendorById(Long id) {
        return vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendor not found with id: " + id));
    }

    // Update Vendor
    public Vendor updateVendor(Long id, Vendor updatedVendor) {

        Vendor existingVendor = getVendorById(id);

        existingVendor.setName(updatedVendor.getName());
        existingVendor.setPhone(updatedVendor.getPhone());
        existingVendor.setEmail(updatedVendor.getEmail());
        existingVendor.setAddress(updatedVendor.getAddress());

        return vendorRepository.save(existingVendor);
    }

    // Activate Vendor
    public Vendor activateVendor(Long id) {

        Vendor vendor = getVendorById(id);

        vendor.setActive(true);

        return vendorRepository.save(vendor);
    }

    // Deactivate Vendor
    public Vendor deactivateVendor(Long id) {

        Vendor vendor = getVendorById(id);

        vendor.setActive(false);

        return vendorRepository.save(vendor);
    }
}
