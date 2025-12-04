package com.egler.petcare.service;

import com.egler.petcare.model.Appointment;
import com.egler.petcare.model.Pet;
import com.egler.petcare.model.ServiceOffering;
import com.egler.petcare.model.User;
import com.egler.petcare.repository.AppointmentRepository;
import com.egler.petcare.repository.PetRepository;
import com.egler.petcare.repository.ServiceOfferingRepository;
import com.egler.petcare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//AppointmentService handles business logic for appointment booking and management
@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private ServiceOfferingRepository serviceOfferingRepository;

    //return all appointments for a user, ordered by most recent date
    public List<Appointment> getAppointmentsByUsername(String username) {
        return appointmentRepository.findByUserUsernameOrderByAppointmentDateDesc(username);
    }

    //create a new appointment
    public Appointment createAppointment(Long petId, Long serviceId, Appointment appointment, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        // Security check: make sure the pet belongs to the user
        if (!pet.getOwner().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized: Pet does not belong to user");
        }

        ServiceOffering service = serviceOfferingRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        appointment.setUser(user);
        appointment.setPet(pet);
        appointment.setService(service);
        appointment.setStatus(Appointment.AppointmentStatus.PENDING);

        return appointmentRepository.save(appointment);
    }

    //find a single appointment by ID
    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    //verify an appointment belongs to a specific user
    public boolean isAppointmentOwnedByUser(Long appointmentId, String username) {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        return appointment.isPresent() && appointment.get().getUser().getUsername().equals(username);
    }

    //delete an appointment
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }
}