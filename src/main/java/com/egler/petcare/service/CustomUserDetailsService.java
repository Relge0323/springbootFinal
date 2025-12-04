package com.egler.petcare.service;

import com.egler.petcare.model.User;
import com.egler.petcare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//CustomUserDetailsService is required by Spring Security for authentication
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    //called by Spring Security during login
    //loads user from database and converts to Spring Security's UserDetails format
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //find user in database by username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        //convert the user to Spring Security's User object
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .disabled(!user.isEnabled())
                .build();
    }
}