package com.dealership.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "salespersons")
public class SalesPerson {

    @Id
    @Column(nullable = false, unique = true)
    private String id;

    @NotBlank(message = "Name is required")
    private String name;

    private String phone;
    private String region;
    private int    vehiclesSold;

    // ===== Original Business Logic (UNCHANGED) =====

    public void recordSale() {
        vehiclesSold++;
    }

    public double commission() {
        return vehiclesSold * 2000;
    }

    public void display() {
        System.out.println("\nSalesperson: " + name);
        System.out.println("Region: "       + region);
        System.out.println("Vehicles Sold: "+ vehiclesSold);
        System.out.println("Commission: "   + commission());
    }

    // ===== Getters & Setters =====

    public String getId()                  { return id; }
    public void   setId(String id)         { this.id = id; }

    public String getName()                { return name; }
    public void   setName(String name)     { this.name = name; }

    public String getPhone()               { return phone; }
    public void   setPhone(String phone)   { this.phone = phone; }

    // HTML form sends "contact" — kept for form compatibility
    public void setContact(String contact) { this.phone = contact; }

    public String getRegion()              { return region; }
    public void   setRegion(String region) { this.region = region; }

    public int  getVehiclesSold()              { return vehiclesSold; }
    public void setVehiclesSold(int vehiclesSold) { this.vehiclesSold = vehiclesSold; }
}
