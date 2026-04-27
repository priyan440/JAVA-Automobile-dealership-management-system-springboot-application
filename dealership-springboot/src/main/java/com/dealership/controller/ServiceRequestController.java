package com.dealership.controller;

import com.dealership.entity.ServiceRequest;
import com.dealership.service.ServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/service")
@CrossOrigin(origins = "*")
public class ServiceRequestController {

    @Autowired
    private ServiceRequestService service;

    /**
     * POST /api/service
     * Body: { user, vehicleId, issue, slot }
     * Original: userMenu case 4 — create service request
     */
    @PostMapping
    public ResponseEntity<ServiceRequest> create(@RequestBody Map<String, String> body) {
        String user      = body.getOrDefault("user",      "");
        String vehicleId = body.getOrDefault("vehicleId", "");
        String issue     = body.getOrDefault("issue",     "");
        String slot      = body.getOrDefault("slot",      "Morning");
        return ResponseEntity.ok(service.create(user, vehicleId, issue, slot));
    }

    /** POST /api/service/save — save full ServiceRequest object */
    @PostMapping("/save")
    public ResponseEntity<ServiceRequest> save(@RequestBody ServiceRequest sr) {
        return ResponseEntity.ok(service.save(sr));
    }

    /** GET /api/service — admin: all service requests */
    @GetMapping
    public ResponseEntity<List<ServiceRequest>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    /** GET /api/service/user/{username} — original: user view my service requests */
    @GetMapping("/user/{username}")
    public ResponseEntity<List<ServiceRequest>> getByUser(@PathVariable String username) {
        return ResponseEntity.ok(service.getByUser(username));
    }

    /** GET /api/service/tech/{techId} — technician portal: my jobs */
    @GetMapping("/tech/{techId}")
    public ResponseEntity<List<ServiceRequest>> getByTech(@PathVariable String techId) {
        return ResponseEntity.ok(service.getByTechnician(techId));
    }

    /** GET /api/service/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<ServiceRequest> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.getById(id));
    }

    /**
     * PATCH /api/service/{id}/assign
     * Body: { technician, techId }
     * Original: admin case 8 — assign technician
     */
    @PatchMapping("/{id}/assign")
    public ResponseEntity<ServiceRequest> assignTechnician(@PathVariable String id,
                                                            @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(service.assignTechnician(
                id, body.get("technician"), body.getOrDefault("techId", "")));
    }

    /**
     * PATCH /api/service/{id}/update
     * Body: { status, diagnosis, cost }
     * Original: technician job update
     */
    @PatchMapping("/{id}/update")
    public ResponseEntity<ServiceRequest> update(@PathVariable String id,
                                                  @RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(service.update(id, body));
    }

    /**
     * PATCH /api/service/{id}/complete
     * Body: { cost, completionDate }
     * Original: completeService() + addServiceDetails()
     */
    @PatchMapping("/{id}/complete")
    public ResponseEntity<ServiceRequest> complete(@PathVariable String id,
                                                    @RequestBody Map<String, Object> body) {
        double cost = ((Number) body.getOrDefault("cost", 0)).doubleValue();
        String date = (String) body.getOrDefault("completionDate", "");
        return ResponseEntity.ok(service.complete(id, cost, date));
    }
}
