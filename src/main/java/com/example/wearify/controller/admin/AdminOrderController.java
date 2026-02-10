package com.example.wearify.controller.admin;

import com.example.wearify.model.Order;
import com.example.wearify.repository.OrderRepository;
import com.example.wearify.repository.ProductRepository;
import com.example.wearify.repository.UserRepository;
import com.example.wearify.repository.AddressRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AdminOrderController(OrderRepository orderRepository, ProductRepository productRepository,
            AddressRepository addressRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String viewOrders(Model model) {
        model.addAttribute("orders", orderRepository.findAll());
        return "admin/order/view_order";
    }

    @GetMapping("/add")
    public String addOrderForm(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("addresses", addressRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        return "admin/order/add_order";
    }

    @PostMapping("/add")
    public String addOrder(@ModelAttribute Order order) {
        orderRepository.save(order);
        return "redirect:/admin/orders";
    }

    @GetMapping("/update/{id}")
    public String updateOrderForm(@PathVariable Long id, Model model) {
        Order order = orderRepository.findById(id).orElse(new Order());
        model.addAttribute("order", order);
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("addresses", addressRepository.findAll());
        return "admin/order/update_order";
    }

    @PostMapping("/update")
    public String updateOrder(@ModelAttribute Order order) {
        // Ensure we don't lose key relationships if form doesn't send them back
        // But for Petstore parity, assuming form sends all data
        orderRepository.save(order);
        return "redirect:/admin/orders";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderRepository.deleteById(id);
        return "redirect:/admin/orders";
    }
}
