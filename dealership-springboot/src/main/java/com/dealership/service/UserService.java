package com.dealership.service;

import com.dealership.entity.User;
import com.dealership.exception.*;
import com.dealership.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // ===== Register (original: verifyEmail + verifyPhone + saveToDB) =====
    public User register(User user) {
        if (userRepository.existsById(user.getUsername())) {
            throw new DuplicateResourceException("Username '" + user.getUsername() + "' already exists.");
        }
        if (!user.verifyEmail()) {
            throw new IllegalArgumentException("Invalid email address.");
        }
        if (!user.verifyPhone()) {
            throw new IllegalArgumentException("Phone must be exactly 10 digits.");
        }
        return userRepository.save(user);
    }

    // ===== Login (original: login(u,p) logic) =====
    public User login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password."));
    }

    // ===== Get All Users (admin) =====
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ===== Get by ID =====
    public User getUser(String username) {
        return userRepository.findById(username)
                .orElseThrow(() -> new ResourceNotFoundException("User '" + username + "' not found."));
    }
}
