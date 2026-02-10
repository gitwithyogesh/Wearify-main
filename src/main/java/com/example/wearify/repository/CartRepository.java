package com.example.wearify.repository;

import com.example.wearify.model.Cart;
import com.example.wearify.model.Product;
import com.example.wearify.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser(User user);

    Optional<Cart> findByUserAndProduct(User user, Product product);
}
