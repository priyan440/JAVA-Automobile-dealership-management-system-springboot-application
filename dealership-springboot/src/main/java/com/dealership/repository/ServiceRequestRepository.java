package com.dealership.repository;

import com.dealership.entity.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, String> {
    List<ServiceRequest> findByUser(String user);
    List<ServiceRequest> findByTechId(String techId);
    List<ServiceRequest> findByStatus(String status);
}
