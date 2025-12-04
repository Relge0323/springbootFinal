package com.egler.petcare.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * ServiceOffering represents a service provided by the business
 * Walking, Grooming, Boarding
 * Each service has a name, description, and price
 */
@Entity
@Table(name = "services")
@Data
@NoArgsConstructor
public class ServiceOffering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Double basePrice;

    @Column(nullable = false)
    private Boolean active = true;

    // constructor for easy creation of service offerings
    public ServiceOffering(String name, String description, Double basePrice) {
        this.name = name;
        this.description = description;
        this.basePrice = basePrice;
        this.active = true;
    }
}