package com.egler.petcare.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pets")
@Data
@NoArgsConstructor
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Pet name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Breed is required")
    @Column(nullable = false)
    private String breed;

    @Positive(message = "Age must be a positive number")
    @Column(nullable = false)
    private Integer age;

    @Positive(message = "Weight must be a positive number")
    @Column(nullable = false)
    private Double weight;

    @Column(length = 500)
    private String specialNeeds;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;
}