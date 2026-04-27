package com.dealership.controller;

import com.dealership.entity.Order;
import com.dealership.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * POST /api/orders/place
     * Body: { customerName, vehicleId, salespersonId, paymentMethod, coupon, emi }
     * Original: userMenu case 3 — buy vehicle flow
     */
    @PostMapping("/place")
    public ResponseEntity<Order> placeOrder(@RequestBody Map<String, Object> body) {
        String  customerName  = (String)  body.getOrDefault("customerName",  "");
        String  vehicleId     = (String)  body.getOrDefault("vehicleId",     "");
        String  salespersonId = (String)  body.getOrDefault("salespersonId", "");
        String  paymentMethod = (String)  body.getOrDefault("paymentMethod", "Cash");
        String  coupon        = (String)  body.getOrDefault("coupon",        "NONE");
        boolean emi           = Boolean.parseBoolean(body.getOrDefault("emi", "false").toString());

        return ResponseEntity.ok(orderService.placeOrder(
                customerName, vehicleId, salespersonId, paymentMethod, coupon, emi));
    }

    /** POST /api/orders — save order directly (salesperson portal) */
    @PostMapping
    public ResponseEntity<Order> saveOrder(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.saveOrder(order));
    }

    /** GET /api/orders — admin: all orders */
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    /** GET /api/orders/{orderId} */
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable String orderId) {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }

    /** GET /api/orders/customer/{name} — original: viewCustomerHistory */
    @GetMapping("/customer/{name}")
    public ResponseEntity<List<Order>> getByCustomer(@PathVariable String name) {
        return ResponseEntity.ok(orderService.getOrdersByCustomer(name));
    }

    /** GET /api/orders/top-vehicle — original: topVehicle() */
    @GetMapping("/top-vehicle")
    public ResponseEntity<Map<String, Object>> topVehicle() {
        return ResponseEntity.ok(orderService.topSellingVehicle());
    }

    /** PATCH /api/orders/{orderId}/cancel */
    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrder(@PathVariable String orderId,
                                              @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(orderService.cancelOrder(orderId, body.get("reason")));
    }
}
