package com.dealership.service;


import com.dealership.entity.Vehicle;
import com.dealership.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private VehicleRepository vehicleRepo;
    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private ServiceRequestRepository serviceRepo;
    @Autowired
    private SalesPersonRepository spRepo;

    /**
     * Original dashboard() logic — counts + low stock warnings
     */
    public Map<String, Object> getDashboard() {
        // Flatten to string list
        List<String> lowStockModels = vehicleRepo.findAll().stream()
                .filter(Vehicle::lowStock)
                .map(Vehicle::getModel)
                .collect(Collectors.toList());

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("totalVehicles", vehicleRepo.count());
        data.put("totalOrders", orderRepo.count());
        data.put("totalServiceRequests", serviceRepo.count());
        data.put("totalSalespersons", spRepo.count());
        data.put("lowStockVehicles", lowStockModels);

        System.out.println("\n========== ADMIN DASHBOARD ==========");
        System.out.println("Total Vehicles in System : " + data.get("totalVehicles"));
        System.out.println("Total Orders Placed      : " + data.get("totalOrders"));
        System.out.println("Total Service Requests   : " + data.get("totalServiceRequests"));
        System.out.println("Total Salespersons       : " + data.get("totalSalespersons"));
        lowStockModels.forEach(m -> System.out.println("Low Stock: " + m));

        return data;
    }
}
