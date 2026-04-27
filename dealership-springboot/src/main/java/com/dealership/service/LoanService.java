package com.dealership.service;

import com.dealership.entity.Loan;
import com.dealership.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    // ===== Calculate EMI (original: createLoan) =====
    public Loan calculateEmi(double amount, int years) {
        Loan loan = new Loan();
        loan.createLoan(amount, years); // original method
        loan.display();                 // original display()
        return loanRepository.save(loan);
    }

    // ===== Compare Plans (original: comparePlans) =====
    public List<Map<String, Object>> comparePlans(double amount) {
        List<Map<String, Object>> results = new ArrayList<>();
        Loan temp = new Loan();
        for (int i = 1; i <= 5; i++) {
            temp.createLoan(amount, i); // original logic
            results.add(Map.of(
                "years",        i,
                "emi",          Math.round(temp.getEmi() * 100.0) / 100.0,
                "totalPayment", Math.round(temp.totalPayment() * 100.0) / 100.0
            ));
        }
        return results;
    }

    // ===== Check Eligibility (original: checkEligibility) =====
    public Map<String, Object> checkEligibility(double salary, double loanAmount, int years) {
        Loan loan = new Loan();
        loan.createLoan(loanAmount, years); // original createLoan
        boolean eligible = loan.checkEligibility(salary); // original checkEligibility
        return Map.of(
            "eligible",     eligible,
            "salary",       salary,
            "emi",          Math.round(loan.getEmi() * 100.0) / 100.0,
            "totalPayment", Math.round(loan.totalPayment() * 100.0) / 100.0,
            "message",      eligible ? "Eligible for EMI" : "Not eligible for EMI"
        );
    }
}
