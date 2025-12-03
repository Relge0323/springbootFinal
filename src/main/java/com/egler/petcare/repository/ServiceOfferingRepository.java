package com.egler.petcare.repository;

import com.egler.petcare.model.ServiceOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceOfferingRepository extends JpaRepository<ServiceOffering, Long> {
    List<ServiceOffering> findByActiveTrue();
    Optional<ServiceOffering> findByName(String name);
}