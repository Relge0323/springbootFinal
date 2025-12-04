package com.egler.petcare.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Appointment represents a scheduled service appointment
 * Links a User, Pet, and Service with a date/time
 * Appointment objects have a status that tracks their lifecycle (PENDING, CONFIRMED, COMPLETED, CANCELLED)
 **/
@Entity
@Table(name = "appointments")
@Data
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceOffering service;

    @NotNull(message = "Appointment date is required")
    @Future(message = "Appointment date must be in the future")
    @Column(nullable = false)
    private LocalDate appointmentDate;

    @NotNull(message = "Appointment time is required")
    @Column(nullable = false)
    private LocalTime appointmentTime;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status = AppointmentStatus.PENDING;

    @Column(length = 1000)
    private String notes;


    public enum AppointmentStatus {
        PENDING, // waiting for approval
        CONFIRMED, // approved by admin
        COMPLETED, // service finished
        CANCELLED // cancelled
    }
}