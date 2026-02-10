package com.example.wearify.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.wearify.model.Cart;
import com.example.wearify.model.Product;
import com.example.wearify.model.User;
import com.example.wearify.repository.CartRepository;
import com.example.wearify.repository.ProductRepository;
import com.example.wearify.repository.UserRepository;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class UserCartController {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public UserCartController(CartRepository cartRepository, UserRepository userRepository,
            ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepository.findByUsername(username).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();

        Cart existingCart = cartRepository.findByUserAndProduct(user, product).orElse(null);

        if (existingCart != null) {
            existingCart.setQuantity(existingCart.getQuantity() + 1);
            cartRepository.save(existingCart);
        } else {
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setProduct(product);
            cart.setQuantity(1);
            cartRepository.save(cart);
        }

        return "redirect:/cart/view";
    }

    @GetMapping("/view")
    public String viewCart(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepository.findByUsername(username).orElseThrow();
        List<Cart> cartItems = cartRepository.findByUser(user);

        int total = cartItems.stream()
                .mapToInt(item -> item.getProduct().getDiscountedPrice() * item.getQuantity())
                .sum();

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);

        return "view_cart";
    }

    @GetMapping("/delete/{id}")
    public String deleteFromCart(@PathVariable Long id) {
        cartRepository.deleteById(id);
        return "redirect:/cart/view";
    }

    @GetMapping("/addQuantity/{id}")
    public String addQuantity(@PathVariable Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow();
        cart.setQuantity(cart.getQuantity() + 1);
        cartRepository.save(cart);
        return "redirect:/cart/view";
    }

    @GetMapping("/deleteQuantity/{id}")
    public String deleteQuantity(@PathVariable Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow();
        if (cart.getQuantity() > 1) {
            cart.setQuantity(cart.getQuantity() - 1);
            cartRepository.save(cart);
        } else {
            cartRepository.deleteById(id);
        }
        return "redirect:/cart/view";
    }
}
