package com.egler.petcare.service;

import com.egler.petcare.model.Pet;
import com.egler.petcare.model.User;
import com.egler.petcare.repository.PetRepository;
import com.egler.petcare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//PetService handles business logic for pet management
//Ensure pets are always linked to owner and enforces security rules
@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UserRepository userRepository;

    //a list of all pets belonging to a user
    public List<Pet> getPetsByUsername(String username) {
        return petRepository.findByOwnerUsername(username);
    }

    //saves new pet or updates an existing one
    public Pet savePet(Pet pet, String username) {
        User owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        pet.setOwner(owner);
        return petRepository.save(pet);
    }

    //find a single pet by ID
    public Optional<Pet> getPetById(Long id) {
        return petRepository.findById(id);
    }

    //delete a pet from the database
    public void deletePet(Long id) {
        petRepository.deleteById(id);
    }

    //verifies a pet belongs to a specific user
    public boolean isPetOwnedByUser(Long petId, String username) {
        Optional<Pet> pet = petRepository.findById(petId);
        return pet.isPresent() && pet.get().getOwner().getUsername().equals(username);
    }
}