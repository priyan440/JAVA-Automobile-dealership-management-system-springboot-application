package com.dealership.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "service_requests")
public class ServiceRequest {

    @Id
    @Column(nullable = false, unique = true)
    private String id;

    private String user;
    private String vehicleId;
    private String issue;
    private String slot;
    private String technician   = "Not Assigned";
    private String techId;
    private String status       = "Pending";
    private String priority     = "Normal";
    private double cost;
    private String completionDate;
    private String diagnosis;
    private String date;

    // ===== Original Constructor Logic (UNCHANGED) =====

    public ServiceRequest() {}

    public ServiceRequest(String user, String vehicleId, String issue, String slot) {
        this.id        = "SR" + System.currentTimeMillis();
        this.user      = user;
        this.vehicleId = vehicleId;
        this.issue     = issue;
        this.slot      = slot;
    }

    // ===== Original Business Logic (UNCHANGED) =====

    public void assignTechnician(String t) {
        this.technician = t;
        this.status     = "In Progress";
    }

    public void completeService() {
        this.status = "Completed";
    }

    public void addServiceDetails(double c, String date) {
        this.cost           = c;
        this.completionDate = date;
    }

    public void display() {
        System.out.println("\nService ID: "  + id);
        System.out.println("User: "          + user);
        System.out.println("Vehicle: "       + vehicleId);
        System.out.println("Issue: "         + issue);
        System.out.println("Slot: "          + slot);
        System.out.println("Technician: "    + technician);
        System.out.println("Status: "        + status);
        if ("Completed".equals(status)) {
            System.out.println("Service Cost: "     + cost);
            System.out.println("Completion Date: "  + completionDate);
        }
    }

    // ===== Getters & Setters =====

    public String getId()                        { return id; }
    public void   setId(String id)               { this.id = id; }

    public String getUser()                      { return user; }
    public void   setUser(String user)           { this.user = user; }

    public String getVehicleId()                 { return vehicleId; }
    public void   setVehicleId(String v)         { this.vehicleId = v; }

    public String getIssue()                     { return issue; }
    public void   setIssue(String issue)         { this.issue = issue; }

    public String getSlot()                      { return slot; }
    public void   setSlot(String slot)           { this.slot = slot; }

    public String getTechnician()                { return technician; }
    public void   setTechnician(String t)        { this.technician = t; }

    public String getTechId()                    { return techId; }
    public void   setTechId(String t)            { this.techId = t; }

    public String getStatus()                    { return status; }
    public void   setStatus(String status)       { this.status = status; }

    public String getPriority()                  { return priority; }
    public void   setPriority(String priority)   { this.priority = priority; }

    public double getCost()                      { return cost; }
    public void   setCost(double cost)           { this.cost = cost; }

    public String getCompletionDate()            { return completionDate; }
    public void   setCompletionDate(String d)    { this.completionDate = d; }

    public String getDiagnosis()                 { return diagnosis; }
    public void   setDiagnosis(String d)         { this.diagnosis = d; }

    public String getDate()                      { return date; }
    public void   setDate(String date)           { this.date = date; }
}
