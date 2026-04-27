package com.dealership;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Automobile Dealership Management System
 * ----------------------------------------
 * Technology Stack:
 *   - Maven        : Build tool
 *   - Spring Boot  : Application framework
 *   - Spring Data JPA : ORM / Database layer
 *   - SQLite       : Embedded database (no setup required)
 *   - REST API     : Controllers under /api/*
 *   - HTML Frontend: Served from /static/DashBoard.html
 *
 * All original business logic from Loan, Vehicle, Order,
 * SalesPerson, ServiceRequest, User, Payment is preserved.
 *
 * Run: mvn spring-boot:run
 * Open: http://localhost:8081
 */
@SpringBootApplication
public class DealershipApplication {
    public static void main(String[] args) {
        SpringApplication.run(DealershipApplication.class, args);
    }
}
