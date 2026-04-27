package com.dealership.service;

import com.dealership.entity.ServiceRequest;
import com.dealership.exception.*;
import com.dealership.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class ServiceRequestService {

    @Autowired
    private ServiceRequestRepository repo;

    // ===== Create (original: new ServiceRequest(user, v, issue, "Morning")) =====
    public ServiceRequest create(String user, String vehicleId, String issue, String slot) {
        ServiceRequest sr = new ServiceRequest(user, vehicleId, issue, slot);
        sr.setDate(LocalDate.now().toString());
        return repo.save(sr);
    }

    // ===== Get All (admin: view all service requests) =====
    public List<ServiceRequest> getAll() {
        return repo.findAll();
    }

    // ===== Get by User (customer history) =====
    public List<ServiceRequest> getByUser(String user) {
        return repo.findByUser(user);
    }

    // ===== Get by Technician =====
    public List<ServiceRequest> getByTechnician(String techId) {
        return repo.findByTechId(techId);
    }

    // ===== Assign Technician (original: assignTechnician(tech)) =====
    public ServiceRequest assignTechnician(String srId, String technician, String techId) {
        ServiceRequest sr = repo.findById(srId)
                .orElseThrow(() -> new ResourceNotFoundException("Service Request '" + srId + "' not found."));
        sr.assignTechnician(technician); // original method
        sr.setTechId(techId);
        return repo.save(sr);
    }

    // ===== Update (technician: status, diagnosis, cost) =====
    public ServiceRequest update(String srId, Map<String, Object> payload) {
        ServiceRequest sr = repo.findById(srId)
                .orElseThrow(() -> new ResourceNotFoundException("Service Request '" + srId + "' not found."));

        if (payload.containsKey("status"))    sr.setStatus((String) payload.get("status"));
        if (payload.containsKey("diagnosis")) sr.setDiagnosis((String) payload.get("diagnosis"));
        if (payload.containsKey("cost"))      sr.setCost(((Number) payload.get("cost")).doubleValue());

        // Original: completeService() and addServiceDetails()
        if ("Completed".equals(sr.getStatus())) {
            sr.completeService();
            sr.addServiceDetails(sr.getCost(), LocalDate.now().toString());
        }

        return repo.save(sr);
    }

    // ===== Complete Service (original: completeService()) =====
    public ServiceRequest complete(String srId, double cost, String completionDate) {
        ServiceRequest sr = repo.findById(srId)
                .orElseThrow(() -> new ResourceNotFoundException("Service Request '" + srId + "' not found."));
        sr.completeService();                         // original
        sr.addServiceDetails(cost, completionDate);   // original
        return repo.save(sr);
    }

    // ===== Get by ID =====
    public ServiceRequest getById(String id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service Request '" + id + "' not found."));
    }

    // ===== Save directly (used by frontend) =====
    public ServiceRequest save(ServiceRequest sr) {
        if (sr.getId() == null || sr.getId().isBlank()) {
            sr.setId("SR" + System.currentTimeMillis());
        }
        if (sr.getDate() == null) {
            sr.setDate(LocalDate.now().toString());
        }
        return repo.save(sr);
    }
}
