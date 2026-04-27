package com.dealership.controller;

import com.dealership.entity.SalesPerson;
import com.dealership.entity.User;
import com.dealership.exception.InvalidCredentialsException;
import com.dealership.repository.SalesPersonRepository;
import com.dealership.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * AuthController — handles the /api/auth/* paths called by Dashboard HTML.
 * Wraps UserService login + UserService register, adding role-aware logic.
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired private UserService           userService;
    @Autowired private SalesPersonRepository spRepo;

    /**
     * POST /api/auth/login
     * Body: { username, password, role }
     * Returns: { ok, user }
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> body) {
        String username = body.getOrDefault("username", "");
        String password = body.getOrDefault("password", "");
        String role     = body.getOrDefault("role", "user");

        Map<String, Object> response = new HashMap<>();

        try {
            if ("admin".equals(role)) {
                // Original: adminUser.equals("admin") && adminPass.equals("admin123")
                if ("admin".equals(username) && "admin123".equals(password)) {
                    response.put("ok", true);
                    response.put("user", Map.of("name", "Administrator", "username", "admin", "role", "admin"));
                    return ResponseEntity.ok(response);
                } else {
                    throw new InvalidCredentialsException("Invalid admin credentials.");
                }
            }

            if ("sp".equals(role)) {
                // Original: salespersonMenu(s) — login by salesperson ID
                Optional<SalesPerson> sp = spRepo.findById(username);
                if (sp.isPresent()) {
                    response.put("ok", true);
                    response.put("user", sp.get());
                    return ResponseEntity.ok(response);
                } else {
                    throw new InvalidCredentialsException("Salesperson not found.");
                }
            }

            // Default: user login — original login(u, p)
            User user = userService.login(username, password);
            response.put("ok", true);
            response.put("user", user);
            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            response.put("ok", false);
            response.put("message", ex.getMessage());
            return ResponseEntity.status(401).body(response);
        }
    }

    /**
     * POST /api/auth/register
     * Body: { username, password, name/firstName+lastName, phone, email, role }
     * Returns: { ok, user }
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, Object> body) {
        Map<String, Object> response = new HashMap<>();

        try {
            String username = (String) body.getOrDefault("username", "");
            String password = (String) body.getOrDefault("password", "");
            String phone    = (String) body.getOrDefault("phone", "");
            String email    = (String) body.getOrDefault("email", "");

            // Support both "name" and "firstName"+"lastName" formats from HTML
            String name = (String) body.getOrDefault("name", "");
            if (name.isBlank()) {
                String first = (String) body.getOrDefault("firstName", "");
                String last  = (String) body.getOrDefault("lastName", "");
                name = (first + " " + last).trim();
            }

            String role = (String) body.getOrDefault("role", "user");

            if ("sp".equals(role)) {
                // Register as SalesPerson
                SalesPerson sp = new SalesPerson();
                sp.setId(username);
                sp.setName(name);
                sp.setPhone(phone);
                sp.setRegion((String) body.getOrDefault("region", ""));
                sp.setVehiclesSold(0);
                spRepo.save(sp);
                response.put("ok", true);
                response.put("user", sp);
            } else {
                // Register as User — original verifyEmail + verifyPhone + saveToDB
                User user = new User(username, password, name, phone, email);
                User saved = userService.register(user);
                response.put("ok", true);
                response.put("user", saved);
            }

            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            response.put("ok", false);
            response.put("message", ex.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }

    /**
     * GET /api/stats — called on page load to check if backend is reachable
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> stats() {
        return ResponseEntity.ok(Map.of("status", "online", "app", "Dealership System"));
    }
}
