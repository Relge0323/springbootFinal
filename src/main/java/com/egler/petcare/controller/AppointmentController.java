package com.egler.petcare.controller;

import com.egler.petcare.model.Appointment;
import com.egler.petcare.model.Pet;
import com.egler.petcare.model.ServiceOffering;
import com.egler.petcare.repository.ServiceOfferingRepository;
import com.egler.petcare.service.AppointmentService;
import com.egler.petcare.service.PetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PetService petService;

    @Autowired
    private ServiceOfferingRepository serviceOfferingRepository;

    //list all appointments for logged-in user
    @GetMapping
    public String listAppointments(Authentication authentication, Model model) {
        String username = authentication.getName();
        List<Appointment> appointments = appointmentService.getAppointmentsByUsername(username);
        model.addAttribute("appointments", appointments);
        return "appointments/list";
    }

    //show book appointment form
    @GetMapping("/book")
    public String showBookingForm(Authentication authentication, Model model) {
        String username = authentication.getName();
        List<Pet> pets = petService.getPetsByUsername(username);

        //check if user has any pets
        if (pets.isEmpty()) {
            return "redirect:/pets/add?needPet=true";
        }

        List<ServiceOffering> services = serviceOfferingRepository.findByActiveTrue();

        model.addAttribute("appointment", new Appointment());
        model.addAttribute("pets", pets);
        model.addAttribute("services", services);
        return "appointments/book";
    }

    //process booking form
    @PostMapping("/book")
    public String bookAppointment(@Valid @ModelAttribute("appointment") Appointment appointment,
                                  BindingResult result,
                                  @RequestParam Long petId,
                                  @RequestParam Long serviceId,
                                  Authentication authentication,
                                  Model model) {
        if (result.hasErrors()) {
            String username = authentication.getName();
            List<Pet> pets = petService.getPetsByUsername(username);
            List<ServiceOffering> services = serviceOfferingRepository.findByActiveTrue();
            model.addAttribute("pets", pets);
            model.addAttribute("services", services);
            return "appointments/book";
        }

        String username = authentication.getName();
        appointmentService.createAppointment(petId, serviceId, appointment, username);
        return "redirect:/appointments?booked=true";
    }

    //view appointment details
    @GetMapping("/{id}")
    public String viewAppointment(@PathVariable Long id,
                                  Authentication authentication,
                                  Model model) {
        String username = authentication.getName();

        if (!appointmentService.isAppointmentOwnedByUser(id, username)) {
            return "redirect:/appointments?error=unauthorized";
        }

        Appointment appointment = appointmentService.getAppointmentById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        model.addAttribute("appointment", appointment);
        return "appointments/view";
    }

    //cancel appointment
    @PostMapping("/cancel/{id}")
    public String cancelAppointment(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();

        if (!appointmentService.isAppointmentOwnedByUser(id, username)) {
            return "redirect:/appointments?error=unauthorized";
        }

        appointmentService.deleteAppointment(id);
        return "redirect:/appointments?cancelled=true";
    }
}