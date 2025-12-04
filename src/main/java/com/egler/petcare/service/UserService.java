package com.egler.petcare.service;

import com.egler.petcare.model.User;
import com.egler.petcare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//UserService handles business logic related to user management
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //register a new user in the system
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        user.setEnabled(true);
        return userRepository.save(user);
    }

    // check if a username is already taken
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    // check if an email is already taken
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
