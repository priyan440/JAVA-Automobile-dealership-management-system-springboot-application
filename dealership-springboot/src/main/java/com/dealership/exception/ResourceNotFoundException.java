package com.dealership.exception;

// ===== Resource Not Found (404) =====
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
