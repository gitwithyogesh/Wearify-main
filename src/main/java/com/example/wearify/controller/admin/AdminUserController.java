package com.example.wearify.controller.admin;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.wearify.model.User;
import com.example.wearify.repository.UserRepository;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminUserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Display all users
    @GetMapping
    public String viewUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin/user/view_user";
    }

    // Show form to add a new user
    @GetMapping("/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/user/add_user";
    }

    // Handle form submission for adding a new user
    @PostMapping("/add")
    public String addUser(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true); // Ensure new admin-created users are enabled
        userRepository.save(user);
        return "redirect:/admin/users";
    }

    // Show form to update an existing user
    @GetMapping("/update/{id}")
    public String updateUserForm(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow();
        model.addAttribute("user", user);
        return "admin/user/update_user";
    }

    // Handle form submission for updating an existing user
    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user) {
        // Retrieve existing user to keep password if not changed (or handle password
        // update logic)
        // For simplicity matching Petstore, we'll just save.
        // Note: In real app, we should check if password field is empty to avoid
        // overwriting with empty hash
        // But following Petstore logic exactly as requested:

        User existingUser = userRepository.findById(user.getId()).orElseThrow();
        existingUser.setUsername(user.getUsername());
        existingUser.setRole(user.getRole());
        existingUser.setEnabled(user.isEnabled());

        // Only update password if a new one is provided (not empty)
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userRepository.save(existingUser);
        return "redirect:/admin/users";
    }

    // Delete a user
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin/users";
    }
}
