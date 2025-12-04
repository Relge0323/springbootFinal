package com.egler.petcare.controller;

import com.egler.petcare.model.Pet;
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
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;

    //list all pets for the logged-in user
    @GetMapping
    public String listPets(Authentication authentication, Model model) {
        String username = authentication.getName();
        List<Pet> pets = petService.getPetsByUsername(username);
        model.addAttribute("pets", pets);
        return "pets/list";
    }

    //show add pet form
    @GetMapping("/add")
    public String showAddPetForm(Model model) {
        model.addAttribute("pet", new Pet());
        return "pets/add";
    }

    //process add pet form
    @PostMapping("/add")
    public String addPet(@Valid @ModelAttribute("pet") Pet pet,
                         BindingResult result,
                         Authentication authentication) {
        if (result.hasErrors()) {
            return "pets/add";
        }

        String username = authentication.getName();
        petService.savePet(pet, username);
        return "redirect:/pets";
    }

    //show edit pet form
    @GetMapping("/edit/{id}")
    public String showEditPetForm(@PathVariable Long id,
                                  Authentication authentication,
                                  Model model) {
        String username = authentication.getName();

        //security check: make sure this pet belongs to the logged-in user
        if (!petService.isPetOwnedByUser(id, username)) {
            return "redirect:/pets?error=unauthorized";
        }

        Pet pet = petService.getPetById(id)
                .orElseThrow(() -> new RuntimeException("Pet not found"));
        model.addAttribute("pet", pet);
        return "pets/edit";
    }

    //process edit pet form
    @PostMapping("/edit/{id}")
    public String editPet(@PathVariable Long id,
                          @Valid @ModelAttribute("pet") Pet pet,
                          BindingResult result,
                          Authentication authentication) {
        if (result.hasErrors()) {
            return "pets/edit";
        }

        String username = authentication.getName();

        //security check
        if (!petService.isPetOwnedByUser(id, username)) {
            return "redirect:/pets?error=unauthorized";
        }

        pet.setId(id);
        petService.savePet(pet, username);
        return "redirect:/pets";
    }

    //delete pet
    @PostMapping("/delete/{id}")
    public String deletePet(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();

        //security check
        if (!petService.isPetOwnedByUser(id, username)) {
            return "redirect:/pets?error=unauthorized";
        }

        petService.deletePet(id);
        return "redirect:/pets?deleted=true";
    }
}