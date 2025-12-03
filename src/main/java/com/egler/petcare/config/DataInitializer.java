package com.egler.petcare.config;

import com.egler.petcare.model.ServiceOffering;
import com.egler.petcare.repository.ServiceOfferingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ServiceOfferingRepository serviceOfferingRepository;

    @Override
    public void run(String... args) throws Exception {
        // Only initialize if services table is empty
        if (serviceOfferingRepository.count() == 0) {
            ServiceOffering boarding = new ServiceOffering(
                    "Boarding",
                    "Safe and comfortable boarding for your pets while you're away. Includes feeding, exercise, and care.",
                    45.00
            );

            ServiceOffering grooming = new ServiceOffering(
                    "Grooming",
                    "Professional grooming services including bath, haircut, nail trimming, and ear cleaning.",
                    65.00
            );

            ServiceOffering walking = new ServiceOffering(
                    "Walking",
                    "Regular walks to keep your dogs happy, healthy, and exercised. Available in 30-minute sessions.",
                    20.00
            );

            serviceOfferingRepository.save(boarding);
            serviceOfferingRepository.save(grooming);
            serviceOfferingRepository.save(walking);

            System.out.println("Initialized default services: Boarding, Grooming, Walking");
        }
    }
}