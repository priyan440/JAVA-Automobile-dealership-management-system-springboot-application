package com.dealership.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @Column(nullable = false, unique = true)
    private String id;

    private String type;

    @NotBlank(message = "Brand is required")
    private String brand;

    @NotBlank(message = "Model is required")
    private String model;

    private String fuelType;

    private int year;

    @Min(value = 0, message = "Stock cannot be negative")
    private int stock;

    @NotNull(message = "Price is required")
    @Min(value = 1, message = "Price must be > 0")
    private double price;

    // ===== Original Business Logic (UNCHANGED) =====

    public double tax() {
        return price * 0.18;
    }

    public double discount() {
        if (price > 1_000_000) return price * 0.10;
        if (price > 500_000)   return price * 0.05;
        return 0;
    }

    public double finalPrice() {
        return price + tax();
    }

    public double finalPriceWithDiscount() {
        return price + tax() - discount();
    }

    public boolean available() {
        return stock > 0;
    }

    public void reduceStock() {
        stock--;
    }

    public boolean lowStock() {
        return stock < 2;
    }

    public void display() {
        System.out.println("\nVehicle ID: " + id);
        System.out.println("Brand: "      + brand);
        System.out.println("Model: "      + model);
        System.out.println("Price: "      + price);
        System.out.println("Stock: "      + stock);
        if (lowStock()) System.out.println("⚠ Low Stock Warning");
    }

    // ===== Getters & Setters =====

    public String getId()              { return id; }
    public void   setId(String id)     { this.id = id; }

    public String getType()            { return type; }
    public void   setType(String t)    { this.type = t; }

    public String getBrand()           { return brand; }
    public void   setBrand(String b)   { this.brand = b; }

    public String getModel()           { return model; }
    public void   setModel(String m)   { this.model = m; }

    public String getFuelType()        { return fuelType; }
    public void   setFuelType(String f){ this.fuelType = f; }

    public int  getYear()              { return year; }
    public void setYear(int y)         { this.year = y; }

    public int  getStock()             { return stock; }
    public void setStock(int s)        { this.stock = s; }

    public double getPrice()           { return price; }
    public void   setPrice(double p)   { this.price = p; }
}
