package com.example.wearify.repository;

import com.example.wearify.model.Order;
import com.example.wearify.model.User;
import com.example.wearify.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserOrderByOrderAtDesc(User user);

    List<Order> findByUserAndStatus(User user, OrderStatus status);
}
