package com.egler.petcare.service;

import com.egler.petcare.model.Pet;
import com.egler.petcare.model.User;
import com.egler.petcare.repository.PetRepository;
import com.egler.petcare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Pet> getPetsByUsername(String username) {
        return petRepository.findByOwnerUsername(username);
    }

    public Pet savePet(Pet pet, String username) {
        User owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        pet.setOwner(owner);
        return petRepository.save(pet);
    }

    public Optional<Pet> getPetById(Long id) {
        return petRepository.findById(id);
    }

    public void deletePet(Long id) {
        petRepository.deleteById(id);
    }

    public boolean isPetOwnedByUser(Long petId, String username) {
        Optional<Pet> pet = petRepository.findById(petId);
        return pet.isPresent() && pet.get().getOwner().getUsername().equals(username);
    }
}