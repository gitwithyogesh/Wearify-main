package com.example.wearify.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.wearify.model.Product;
import com.example.wearify.model.enums.Category;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);
}
