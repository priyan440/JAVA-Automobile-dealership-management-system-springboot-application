package com.dealership.exception;

// ===== Duplicate Entry (409 Conflict) =====
public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}
