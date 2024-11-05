package com.example.responsiveregistration.controllers;

import com.example.responsiveregistration.models.User;
import com.example.responsiveregistration.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@Valid User user, BindingResult result, RedirectAttributes redirectAttributes) {
        // Check for existing email first
        if (userRepository.existsByEmail(user.getEmail())) {
            result.rejectValue("email", "error.user", "An account with this email already exists.");
            return "register";
        }

        if (result.hasErrors()) {
            return "register";
        }

        // Ensure the password is not null and encode it
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            result.rejectValue("password", "error.user", "Password cannot be null or empty.");
            return "register";
        }

        // Encode and set the password
        user.setHashedPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        // Redirect to login with a success message
        redirectAttributes.addFlashAttribute("message", "Registration successful! Welcome!");
        return "redirect:/login";
    }
}
