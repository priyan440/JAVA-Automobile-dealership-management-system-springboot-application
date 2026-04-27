package com.dealership.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(nullable = false, unique = true)
    private String orderId;

    private String customerName;

    // Stored as simple columns (denormalized for SQLite simplicity, mirrors original flat file approach)
    private String vehicleModel;
    private String vehicleId;
    private String salespersonName;
    private String salespersonId;
    private String paymentMethod;
    private double amount;
    private boolean emi;
    private String  status      = "Pending";
    private String  cancelReason;
    private String  orderDate;

    // ===== Original Business Logic (UNCHANGED) =====

    /** Original invoice() logic */
    public void invoice() {
        System.out.println("\n===== INVOICE =====");
        System.out.println("Order ID: "      + orderId);
        System.out.println("Customer: "      + customerName);
        System.out.println("Vehicle: "       + vehicleModel);
        System.out.println("Salesperson: "   + salespersonName);
        System.out.println("Amount Paid: "   + amount);
        System.out.println("Payment: "       + paymentMethod);
        System.out.println("EMI: "           + (emi ? "Yes" : "No"));
    }

    /** Original advancedInvoice() logic */
    public void advancedInvoice() {
        System.out.println("\n===== ADVANCED INVOICE =====");
        invoice();
    }

    /** Original coupon logic (UNCHANGED) */
    public double applyCoupon(double amount, String coupon) {
        if (coupon.equalsIgnoreCase("SAVE10")) {
            System.out.println("Coupon Applied!");
            return amount * 0.9;
        }
        return amount;
    }

    // ===== Getters & Setters =====

    public String getOrderId()                   { return orderId; }
    public void   setOrderId(String orderId)     { this.orderId = orderId; }

    public String getCustomerName()              { return customerName; }
    public void   setCustomerName(String c)      { this.customerName = c; }

    public String getVehicleModel()              { return vehicleModel; }
    public void   setVehicleModel(String v)      { this.vehicleModel = v; }

    public String getVehicleId()                 { return vehicleId; }
    public void   setVehicleId(String v)         { this.vehicleId = v; }

    public String getSalespersonName()           { return salespersonName; }
    public void   setSalespersonName(String s)   { this.salespersonName = s; }

    public String getSalespersonId()             { return salespersonId; }
    public void   setSalespersonId(String s)     { this.salespersonId = s; }

    public String getPaymentMethod()             { return paymentMethod; }
    public void   setPaymentMethod(String p)     { this.paymentMethod = p; }

    public double getAmount()                    { return amount; }
    public void   setAmount(double amount)       { this.amount = amount; }

    public boolean isEmi()                       { return emi; }
    public void    setEmi(boolean emi)           { this.emi = emi; }

    public String getStatus()                    { return status; }
    public void   setStatus(String status)       { this.status = status; }

    public String getCancelReason()              { return cancelReason; }
    public void   setCancelReason(String r)      { this.cancelReason = r; }

    public String getOrderDate()                 { return orderDate; }
    public void   setOrderDate(String d)         { this.orderDate = d; }
}
