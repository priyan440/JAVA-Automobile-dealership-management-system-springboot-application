package com.dealership.controller;

import com.dealership.entity.Loan;
import com.dealership.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/loans")
@CrossOrigin(origins = "*")
public class LoanController {

    @Autowired
    private LoanService loanService;

    /**
     * POST /api/loans/calculate
     * Body: { amount, years }
     * Original: createLoan(amount, years) + display()
     */
    @PostMapping("/calculate")
    public ResponseEntity<Loan> calculateEmi(@RequestBody Map<String, Object> body) {
        double amount = ((Number) body.get("amount")).doubleValue();
        int    years  = ((Number) body.get("years")).intValue();
        return ResponseEntity.ok(loanService.calculateEmi(amount, years));
    }

    /**
     * GET /api/loans/compare?amount=500000
     * Original: comparePlans(amount) — 1 to 5 year comparison
     */
    @GetMapping("/compare")
    public ResponseEntity<List<Map<String, Object>>> comparePlans(@RequestParam double amount) {
        return ResponseEntity.ok(loanService.comparePlans(amount));
    }

    /**
     * GET /api/loans/eligibility?salary=25000&amount=500000&years=3
     * Original: checkEligibility(salary)
     */
    @GetMapping("/eligibility")
    public ResponseEntity<Map<String, Object>> checkEligibility(
            @RequestParam double salary,
            @RequestParam double amount,
            @RequestParam int    years) {
        return ResponseEntity.ok(loanService.checkEligibility(salary, amount, years));
    }
}
