package com.dealership.controller;

import com.dealership.entity.Vehicle;
import com.dealership.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@CrossOrigin(origins = "*")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    /** POST /api/vehicles — original: admin case 2 Add New Vehicle */
    @PostMapping
    public ResponseEntity<Vehicle> addVehicle(@Valid @RequestBody Vehicle vehicle) {
        return ResponseEntity.ok(vehicleService.addVehicle(vehicle));
    }

    /** GET /api/vehicles — original: loadVehicles */
    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    /** GET /api/vehicles/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicle(@PathVariable String id) {
        return ResponseEntity.ok(vehicleService.getVehicle(id));
    }

    /** GET /api/vehicles/brand/{brand} — original: case 2 search by brand */
    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<Vehicle>> getByBrand(@PathVariable String brand) {
        return ResponseEntity.ok(vehicleService.getByBrand(brand));
    }

    /** GET /api/vehicles/recommend?budget=500000 — original: recommendVehicle() */
    @GetMapping("/recommend")
    public ResponseEntity<List<Vehicle>> recommend(@RequestParam double budget) {
        return ResponseEntity.ok(vehicleService.recommendByBudget(budget));
    }

    /** GET /api/vehicles/low-stock — original: lowStock() dashboard warning */
    @GetMapping("/low-stock")
    public ResponseEntity<List<Vehicle>> lowStock() {
        return ResponseEntity.ok(vehicleService.getLowStockVehicles());
    }

    /** PUT /api/vehicles/{id} — update vehicle */
    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable String id,
                                                  @Valid @RequestBody Vehicle updated) {
        return ResponseEntity.ok(vehicleService.updateVehicle(id, updated));
    }

    /** DELETE /api/vehicles/{id} */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVehicle(@PathVariable String id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.ok("Vehicle deleted successfully.");
    }
}
