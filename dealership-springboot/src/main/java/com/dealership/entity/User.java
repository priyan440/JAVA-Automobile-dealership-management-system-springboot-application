package com.dealership.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "Password is required")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Name is required")
    private String name;

    @Size(min = 10, max = 10, message = "Phone must be 10 digits")
    private String phone;

    @Email(message = "Invalid email format")
    private String email;

    // ===== Constructors =====

    public User() {} // Required by JPA & Spring

    public User(String username, String password, String name, String phone, String email) {
        this.username = username;
        this.password = password;
        this.name     = name;
        this.phone    = phone;
        this.email    = email;
    }

    // ===== Original Business Logic (UNCHANGED) =====

    public boolean login(String u, String p) {
        return username.equals(u) && password.equals(p);
    }

    public boolean verifyEmail() {
        return email != null && email.contains("@") && email.contains(".");
    }

    public boolean verifyPhone() {
        return phone != null && phone.length() == 10;
    }

    public void display() {
        System.out.println("Name: "  + name);
        System.out.println("Phone: " + phone);
        System.out.println("Email: " + email);
    }

    // ===== Getters & Setters =====

    public String getUsername()                  { return username; }
    public void   setUsername(String username)   { this.username = username; }

    public String getPassword()                  { return password; }
    public void   setPassword(String password)   { this.password = password; }

    public String getName()                      { return name; }
    public void   setName(String name)           { this.name = name; }

    public String getPhone()                     { return phone; }
    public void   setPhone(String phone)         { this.phone = phone; }

    public String getEmail()                     { return email; }
    public void   setEmail(String email)         { this.email = email; }
}
