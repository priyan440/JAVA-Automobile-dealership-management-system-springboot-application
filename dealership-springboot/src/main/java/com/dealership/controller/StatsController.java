package com.dealership.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Handles utility endpoints called by the Dashboard HTML:
 *   GET /api/stats  — health-check on page load
 *   GET /api/health — health-check
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class StatsController {

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> stats() {
        return ResponseEntity.ok(Map.of(
            "status", "online",
            "app",    "Automobile Dealership System",
            "db",     "SQLite"
        ));
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        return ResponseEntity.ok(Map.of("status", "UP"));
    }
}
