package com.dealership.service;

import com.dealership.entity.SalesPerson;
import com.dealership.exception.*;
import com.dealership.repository.SalesPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class SalesPersonService {

    @Autowired
    private SalesPersonRepository repo;

    // ===== Add Salesperson (original: sp.saveToFile + sp.saveToDB) =====
    public SalesPerson add(SalesPerson sp) {
        if (repo.existsById(sp.getId())) {
            throw new DuplicateResourceException("Salesperson ID '" + sp.getId() + "' already exists.");
        }
        return repo.save(sp);
    }

    // ===== Get All (original: loadSalespersons) =====
    public List<SalesPerson> getAll() {
        return repo.findAll();
    }

    // ===== Get by ID (original: salespersonMenu login) =====
    public SalesPerson getById(String id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Salesperson '" + id + "' not found."));
    }

    // ===== Leaderboard (original: leaderboard() + sort by vehiclesSold desc) =====
    public List<SalesPerson> getLeaderboard() {
        List<SalesPerson> all = repo.findAll();
        all.sort(Comparator.comparingInt(SalesPerson::getVehiclesSold).reversed());
        return all;
    }

    // ===== Performance Report (original: performanceReport()) =====
    public List<SalesPerson> getPerformanceReport() {
        List<SalesPerson> all = repo.findAll();
        if (all.isEmpty()) {
            throw new ResourceNotFoundException("No salespersons registered.");
        }
        all.forEach(SalesPerson::display); // original display() still called
        return all;
    }

    // ===== Commission for one salesperson (original: commission()) =====
    public double getCommission(String id) {
        return getById(id).commission();
    }

    // ===== Update =====
    public SalesPerson update(String id, SalesPerson updated) {
        SalesPerson sp = getById(id);
        sp.setName(updated.getName());
        sp.setPhone(updated.getPhone());
        sp.setRegion(updated.getRegion());
        sp.setVehiclesSold(updated.getVehiclesSold());
        return repo.save(sp);
    }
}
