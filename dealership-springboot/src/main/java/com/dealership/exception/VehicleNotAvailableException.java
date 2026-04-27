package com.dealership.exception;

// ===== Vehicle Out of Stock (400 Bad Request) =====
public class VehicleNotAvailableException extends RuntimeException {
    public VehicleNotAvailableException(String message) {
        super(message);
    }
}
