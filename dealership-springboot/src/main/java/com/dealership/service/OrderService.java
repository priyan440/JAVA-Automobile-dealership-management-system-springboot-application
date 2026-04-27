package com.dealership.service;

import com.dealership.entity.*;
import com.dealership.exception.*;
import com.dealership.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired private OrderRepository       orderRepository;
    @Autowired private VehicleRepository     vehicleRepository;
    @Autowired private SalesPersonRepository salesPersonRepository;

    /**
     * Place an order — mirrors original Dealership.java userMenu case 3 logic:
     *  - Validate vehicle availability
     *  - Apply SAVE10 coupon (original applyCoupon logic)
     *  - Add tax (original finalPrice logic)
     *  - Record sale on salesperson (original recordSale)
     *  - Reduce stock (original reduceStock)
     *  - Save to DB
     */
    public Order placeOrder(String customerName, String vehicleId,
                            String salespersonId, String paymentMethod,
                            String coupon, boolean emi) {

        // Validate vehicle
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle '" + vehicleId + "' not found."));

        if (!vehicle.available()) {
            throw new VehicleNotAvailableException("Vehicle '" + vehicleId + "' is out of stock.");
        }

        // Validate salesperson
        SalesPerson sp = salesPersonRepository.findById(salespersonId)
                .orElseThrow(() -> new ResourceNotFoundException("Salesperson '" + salespersonId + "' not found."));

        // Original: finalPrice = price + tax (18%)
        double finalAmount = vehicle.finalPrice();

        // Original: applyCoupon("SAVE10") -> 10% off
        Order tempOrder = new Order();
        finalAmount = tempOrder.applyCoupon(finalAmount, coupon);

        // Build order
        Order order = new Order();
        order.setOrderId("ORD" + System.currentTimeMillis());
        order.setCustomerName(customerName);
        order.setVehicleModel(vehicle.getModel());
        order.setVehicleId(vehicleId);
        order.setSalespersonName(sp.getName());
        order.setSalespersonId(salespersonId);
        order.setPaymentMethod(paymentMethod);
        order.setAmount(finalAmount);
        order.setEmi(emi);
        order.setStatus("Pending");
        order.setOrderDate(LocalDate.now().toString());

        // Original: recordSale() + reduceStock()
        sp.recordSale();
        vehicle.reduceStock();

        salesPersonRepository.save(sp);
        vehicleRepository.save(vehicle);

        Order saved = orderRepository.save(order);

        // Original: invoice()
        saved.invoice();

        return saved;
    }

    // ===== View Purchase History (original: viewCustomerHistory) =====
    public List<Order> getOrdersByCustomer(String customerName) {
        List<Order> orders = orderRepository.findByCustomerName(customerName);
        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("No orders found for customer: " + customerName);
        }
        return orders;
    }

    // ===== All Orders (admin) =====
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // ===== Top Selling Vehicle (original: topVehicle()) =====
    public Map<String, Object> topSellingVehicle() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("No orders placed yet.");
        }

        Map<String, Long> counts = orders.stream()
                .collect(Collectors.groupingBy(Order::getVehicleModel, Collectors.counting()));

        String best = counts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");

        long sales = counts.getOrDefault(best, 0L);
        return Map.of("vehicle", best, "sales", sales);
    }

    // ===== Cancel Order =====
    public Order cancelOrder(String orderId, String reason) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order '" + orderId + "' not found."));
        order.setStatus("Cancelled");
        order.setCancelReason(reason);
        return orderRepository.save(order);
    }

    // ===== Get Order by ID =====
    public Order getOrder(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order '" + orderId + "' not found."));
    }

    // ===== Save directly (used by salesperson portal) =====
    public Order saveOrder(Order order) {
        if (order.getOrderId() == null || order.getOrderId().isBlank()) {
            order.setOrderId("ORD" + System.currentTimeMillis());
        }
        if (order.getOrderDate() == null) {
            order.setOrderDate(LocalDate.now().toString());
        }
        return orderRepository.save(order);
    }
}
