package com.egler.petcare.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {

        //get the logged-in username
        String username = authentication.getName();
        model.addAttribute("username", username);
        return "dashboard";
    }
}