package com.dealership.exception;

// ===== Bad Credentials (401 Unauthorized) =====
public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
