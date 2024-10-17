package com.example.responsiveregistration.controllers;

import com.example.responsiveregistration.models.User;
import com.example.responsiveregistration.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(
            @Valid @ModelAttribute("user") User user,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            @RequestParam("password") String rawPassword,
            @RequestParam("username") String username,
            @RequestParam("email") String email
    ) {

        if (userRepository.existsByEmail(email)) {
            result.rejectValue("email", "error.user", "An account with this email already exists.");
        }
        if (userRepository.existsByUsername(username)) {
            result.rejectValue("username", "error.user", "This username is already taken.");
        }

        if (result.hasErrors()) {
            return "register";
        }

        String hashedPassword = passwordEncoder.encode(rawPassword);
        user.setHashedPassword(hashedPassword);

        userRepository.save(user);
        redirectAttributes.addFlashAttribute("message", "Registration successful! Welcome!");

        return "redirect:/register";
    }
}
