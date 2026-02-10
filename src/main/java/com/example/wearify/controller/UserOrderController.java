package com.example.wearify.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.wearify.model.Address;
import com.example.wearify.model.Cart;
import com.example.wearify.model.Order;
import com.example.wearify.model.User;
import com.example.wearify.repository.AddressRepository;
import com.example.wearify.repository.CartRepository;
import com.example.wearify.repository.OrderRepository;
import com.example.wearify.repository.UserRepository;

import java.util.List;

@Controller
@RequestMapping("/order")
public class UserOrderController {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public UserOrderController(OrderRepository orderRepository, CartRepository cartRepository,
            UserRepository userRepository, AddressRepository addressRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    @PostMapping("/place")
    public String placeOrder(@RequestParam Long addressId, @RequestParam String paymentMethod) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepository.findByUsername(username).orElseThrow();
        Address address = addressRepository.findById(addressId).orElseThrow();
        List<Cart> cartItems = cartRepository.findByUser(user);

        for (Cart cartItem : cartItems) {
            Order order = new Order();
            order.setUser(user);
            order.setProduct(cartItem.getProduct());
            order.setQuantity(cartItem.getQuantity());
            order.setAddress(address);
            order.setPaymentMethod(paymentMethod);

            if ("COD".equals(paymentMethod)) {
                order.setPaymentStatus("PENDING");
            } else {
                order.setPaymentStatus("PAID"); // Simulate successful payment for online methods
            }

            orderRepository.save(order);
        }

        // Clear cart after order
        cartRepository.deleteAll(cartItems);

        return "redirect:/order/success";
    }

    @GetMapping("/success")
    public String orderSuccess() {
        return "success";
    }

    @GetMapping
    public String viewOrders(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepository.findByUsername(username).orElseThrow();
        List<Order> orders = orderRepository.findByUserOrderByOrderAtDesc(user);

        model.addAttribute("orders", orders);
        return "order";
    }
}
