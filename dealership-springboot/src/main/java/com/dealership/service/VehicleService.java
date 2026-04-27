package com.dealership.service;

import com.dealership.entity.Vehicle;
import com.dealership.exception.*;
import com.dealership.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    // ===== Add Vehicle (original: saveToFile + saveToDB) =====
    public Vehicle addVehicle(Vehicle vehicle) {
        if (vehicleRepository.existsById(vehicle.getId())) {
            throw new DuplicateResourceException("Vehicle ID '" + vehicle.getId() + "' already exists.");
        }
        return vehicleRepository.save(vehicle);
    }

    // ===== Get All (original: loadVehicles) =====
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    // ===== Get by ID =====
    public Vehicle getVehicle(String id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle '" + id + "' not found."));
    }

    // ===== Search by Brand (original: search by brand in userMenu) =====
    public List<Vehicle> getByBrand(String brand) {
        List<Vehicle> result = vehicleRepository.findByBrandIgnoreCase(brand);
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("No vehicles found for brand: " + brand);
        }
        return result;
    }

    // ===== Recommend by Budget (original: recommendVehicle()) =====
    public List<Vehicle> recommendByBudget(double budget) {
        List<Vehicle> result = vehicleRepository.findByPriceLessThanEqual(budget);
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("No vehicles found under budget: " + budget);
        }
        return result;
    }

    // ===== Low Stock (original: lowStock() + dashboard warning) =====
    public List<Vehicle> getLowStockVehicles() {
        return vehicleRepository.findAll().stream()
                .filter(Vehicle::lowStock)
                .collect(Collectors.toList());
    }

    // ===== Update Stock (original: reduceStock()) =====
    public Vehicle reduceStock(String id) {
        Vehicle v = getVehicle(id);
        if (!v.available()) {
            throw new VehicleNotAvailableException("Vehicle '" + id + "' is out of stock.");
        }
        v.reduceStock();
        return vehicleRepository.save(v);
    }

    // ===== Update Vehicle =====
    public Vehicle updateVehicle(String id, Vehicle updated) {
        Vehicle v = getVehicle(id);
        v.setType(updated.getType());
        v.setBrand(updated.getBrand());
        v.setModel(updated.getModel());
        v.setFuelType(updated.getFuelType());
        v.setYear(updated.getYear());
        v.setStock(updated.getStock());
        v.setPrice(updated.getPrice());
        return vehicleRepository.save(v);
    }

    // ===== Delete Vehicle =====
    public void deleteVehicle(String id) {
        if (!vehicleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicle '" + id + "' not found.");
        }
        vehicleRepository.deleteById(id);
    }
}
