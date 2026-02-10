package com.example.wearify.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.wearify.model.Address;
import com.example.wearify.model.User;
import com.example.wearify.model.enums.State;
import com.example.wearify.repository.AddressRepository;
import com.example.wearify.repository.UserRepository;

@Controller
@RequestMapping("/address")
public class AddressController {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressController(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/add")
    public String addAddress(@RequestParam String name,
            @RequestParam String area,
            @RequestParam String city,
            @RequestParam State state,
            @RequestParam Integer pincode) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepository.findByUsername(username).orElseThrow();

        Address address = new Address();
        address.setName(name);
        address.setArea(area);
        address.setCity(city);
        address.setState(state);
        address.setPincode(pincode);
        address.setUser(user);

        addressRepository.save(address);

        return "redirect:/checkout";
    }

    @GetMapping("/delete/{id}")
    public String deleteAddress(@PathVariable Long id) {
        addressRepository.deleteById(id);
        return "redirect:/checkout";
    }
}
