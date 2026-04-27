package com.dealership.controller;

import com.dealership.entity.SalesPerson;
import com.dealership.service.SalesPersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/salespersons")
@CrossOrigin(origins = "*")
public class SalesPersonController {

    @Autowired
    private SalesPersonService service;

    /** POST /api/salespersons — original: admin case 4 Add Salesperson */
    @PostMapping
    public ResponseEntity<SalesPerson> add(@Valid @RequestBody SalesPerson sp) {
        return ResponseEntity.ok(service.add(sp));
    }

    /** GET /api/salespersons — original: loadSalespersons */
    @GetMapping
    public ResponseEntity<List<SalesPerson>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    /** GET /api/salespersons/{id} — original: salesperson login lookup */
    @GetMapping("/{id}")
    public ResponseEntity<SalesPerson> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.getById(id));
    }

    /** GET /api/salespersons/leaderboard — original: leaderboard() */
    @GetMapping("/leaderboard")
    public ResponseEntity<List<SalesPerson>> leaderboard() {
        return ResponseEntity.ok(service.getLeaderboard());
    }

    /** GET /api/salespersons/{id}/commission — original: commission() */
    @GetMapping("/{id}/commission")
    public ResponseEntity<Map<String, Object>> commission(@PathVariable String id) {
        double c = service.getCommission(id);
        return ResponseEntity.ok(Map.of("salespersonId", id, "commission", c));
    }

    /** GET /api/salespersons/performance — original: performanceReport() */
    @GetMapping("/performance")
    public ResponseEntity<List<SalesPerson>> performance() {
        return ResponseEntity.ok(service.getPerformanceReport());
    }

    /** PUT /api/salespersons/{id} */
    @PutMapping("/{id}")
    public ResponseEntity<SalesPerson> update(@PathVariable String id,
                                               @RequestBody SalesPerson updated) {
        return ResponseEntity.ok(service.update(id, updated));
    }
}
