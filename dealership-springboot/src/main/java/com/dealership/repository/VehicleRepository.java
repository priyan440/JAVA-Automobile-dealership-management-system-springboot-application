package com.dealership.repository;

import com.dealership.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {
    List<Vehicle> findByBrandIgnoreCase(String brand);
    List<Vehicle> findByPriceLessThanEqual(double budget);
    List<Vehicle> findByStockGreaterThan(int minStock);
}
