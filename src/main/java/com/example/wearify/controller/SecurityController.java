package com.example.wearify.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.wearify.model.User;
import com.example.wearify.repository.UserRepository;

@Controller
public class SecurityController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SecurityController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login(jakarta.servlet.http.HttpSession session) {
        // Clear error message after it's displayed
        session.removeAttribute("loginError");
        return "login";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");
        userRepository.save(user);
        return "redirect:/login";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin/admin";
    }
}
