package com.egler.petcare.repository;

import com.egler.petcare.model.Pet;
import com.egler.petcare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByOwner(User owner);
    List<Pet> findByOwnerUsername(String username);

}
