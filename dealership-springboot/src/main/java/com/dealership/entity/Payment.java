package com.dealership.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @Column(nullable = false, unique = true)
    private String paymentId;

    private String method;
    private double amount;
    private String status;
    private String cardNumber;
    private String upiId;

    // ===== Original Constructor Logic (UNCHANGED) =====

    public Payment() {}

    public Payment(String method, double amount) {
        this.paymentId = "PAY" + System.currentTimeMillis();
        this.method    = method;
        this.amount    = amount;
        this.status    = "SUCCESS";
    }

    // ===== Original Business Logic (UNCHANGED) =====

    public void collectAndValidate(String cardNumber, String upiId) {
        if ("Card".equalsIgnoreCase(method)) {
            this.cardNumber = cardNumber;
            if (cardNumber == null || cardNumber.length() != 16) {
                System.out.println("Invalid Card Number!");
            }
        } else if ("UPI".equalsIgnoreCase(method)) {
            this.upiId = upiId;
            if (upiId == null || !upiId.contains("@")) {
                System.out.println("Invalid UPI ID!");
            }
        }
    }

    public void display() {
        System.out.println("Payment ID: "  + paymentId);
        System.out.println("Method: "      + method);
        System.out.println("Amount: "      + amount);
        System.out.println("Status: "      + status);
        System.out.println("Card Number: " + cardNumber);
        System.out.println("UPI ID: "      + upiId);
    }

    // ===== Getters & Setters =====

    public String getPaymentId()                 { return paymentId; }
    public void   setPaymentId(String paymentId) { this.paymentId = paymentId; }

    public String getMethod()                    { return method; }
    public void   setMethod(String method)       { this.method = method; }

    public double getAmount()                    { return amount; }
    public void   setAmount(double amount)       { this.amount = amount; }

    public String getStatus()                    { return status; }
    public void   setStatus(String status)       { this.status = status; }

    public String getCardNumber()                { return cardNumber; }
    public void   setCardNumber(String c)        { this.cardNumber = c; }

    public String getUpiId()                     { return upiId; }
    public void   setUpiId(String u)             { this.upiId = u; }
}
