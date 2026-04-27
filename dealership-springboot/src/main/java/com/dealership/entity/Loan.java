package com.dealership.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double loanAmount;
    private double interestRate = 9;
    private int    tenure;
    private double emi;

    // ===== Original Business Logic (UNCHANGED) =====

    public void createLoan(double amount, int years) {
        this.loanAmount = amount;
        this.tenure     = years;

        double r = interestRate / (12 * 100);
        int    n = years * 12;

        this.emi = (loanAmount * r * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1);
    }

    /** 🔥 NEW — compares EMI across 1–5 year plans */
    public void comparePlans(double amount) {
        System.out.println("\nEMI Comparison:");
        for (int i = 1; i <= 5; i++) {
            createLoan(amount, i);
            System.out.println(i + " years EMI: " + emi);
        }
    }

    public double totalPayment() {
        return emi * tenure * 12;
    }

    public void display() {
        System.out.println("Loan Amount: "   + loanAmount);
        System.out.println("Monthly EMI: "   + emi);
        System.out.println("Total Payment: " + totalPayment());
    }

    /** EMI Eligibility check (ADDED) */
    public boolean checkEligibility(double salary) {
        if (salary < 20_000) {
            System.out.println("Not eligible for EMI");
            return false;
        }
        return true;
    }

    // ===== Getters & Setters =====

    public Long   getId()                       { return id; }
    public void   setId(Long id)                { this.id = id; }

    public double getLoanAmount()               { return loanAmount; }
    public void   setLoanAmount(double v)       { this.loanAmount = v; }

    public double getInterestRate()             { return interestRate; }
    public void   setInterestRate(double v)     { this.interestRate = v; }

    public int  getTenure()                     { return tenure; }
    public void setTenure(int v)               { this.tenure = v; }

    public double getEmi()                      { return emi; }
    public void   setEmi(double v)             { this.emi = v; }
}
