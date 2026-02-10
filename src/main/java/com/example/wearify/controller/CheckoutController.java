package com.example.wearify.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.wearify.model.Address;
import com.example.wearify.model.Cart;
import com.example.wearify.model.User;
import com.example.wearify.repository.AddressRepository;
import com.example.wearify.repository.CartRepository;
import com.example.wearify.repository.ProductRepository;
import com.example.wearify.repository.UserRepository;
import java.util.List;

@Controller
public class CheckoutController {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;

    public CheckoutController(CartRepository cartRepository, UserRepository userRepository,
            AddressRepository addressRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepository.findByUsername(username).orElseThrow();
        List<Cart> cartItems = cartRepository.findByUser(user);
        List<Address> addresses = addressRepository.findAllByUser(user);

        int subtotal = cartItems.stream()
                .mapToInt(item -> item.getProduct().getDiscountedPrice() * item.getQuantity())
                .sum();

        int shipping = 0; // Free shipping
        int total = subtotal + shipping;

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("addresses", addresses);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("shipping", shipping);
        model.addAttribute("total", total);
        model.addAttribute("products", productRepository.findAll());

        return "checkout";
    }
}
