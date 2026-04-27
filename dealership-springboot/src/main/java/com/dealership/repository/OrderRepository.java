package com.dealership.repository;

import com.dealership.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByCustomerName(String customerName);
    List<Order> findByVehicleModel(String vehicleModel);
}
