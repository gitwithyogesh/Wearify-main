package com.example.wearify.config;

import com.example.wearify.model.User;
import com.example.wearify.model.enums.Category;
import com.example.wearify.model.Product;
import com.example.wearify.repository.UserRepository;
import com.example.wearify.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository,
            ProductRepository productRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            // Check if admin already exists
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123")); // Password: admin123
                admin.setRole("ADMIN");
                admin.setEnabled(true);
                userRepository.save(admin);
                System.out.println("✅ Generated Admin User: admin / admin123");
            }

            // Check if test user already exists
            if (userRepository.findByUsername("user").isEmpty()) {
                User user = new User();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("user123")); // Password: user123
                user.setRole("USER");
                user.setEnabled(true);
                userRepository.save(user);
                System.out.println("✅ Generated Normal User: user / user123");
            }

            // Add some dummy products if none exist (Optional)
            // Seed Products if table is empty
            if (productRepository.count() == 0) {
                System.out.println("🌱 Seeding Products...");

                // Mens Products
                createProduct(productRepository, "Classic White Shirt", Category.MENS,
                        "Essential cotton shirt for every wardrobe.",
                        "Crafted from premium Egyptian cotton, this classic white shirt offers breathable comfort and a tailored fit perfect for formal or casual occasions.",
                        2500, 1500);

                createProduct(productRepository, "Slim Fit Chinos", Category.MENS,
                        "Versatile beige chinos.",
                        "Modern slim fit chinos made with stretch fabric for ultimate comfort and mobility. ideal for office wear or weekend outings.",
                        3000, 1800);

                createProduct(productRepository, "Leather Jacket", Category.MENS,
                        "Premium genuine leather jacket.",
                        "A timeless rugged leather jacket featuring high-quality hardware and a comfortable lining. Adds an edge to any outfit.",
                        12000, 8500);

                // Womens Products
                createProduct(productRepository, "Floral Summer Dress", Category.WOMENS,
                        "Lightweight breeze dress.",
                        "Beautiful floral print dress perfect for summer days. Features a flattering silhouette and soft, breathable fabric.",
                        4000, 2200);

                createProduct(productRepository, "Elegant Evening Gown", Category.WOMENS,
                        "Sophisticated black evening gown.",
                        "Stunning floor-length gown with subtle embellishments. Designed for elegance and grace at formal events.",
                        15000, 10500);

                createProduct(productRepository, "High-Waist Jeans", Category.WOMENS,
                        "Comfortable vintage style jeans.",
                        "Classic high-waist denim jeans with a relaxed fit. Durable and stylish, suitable for everyday wear.",
                        3500, 1999);

                // Accessories
                createProduct(productRepository, "Leather Wallet", Category.ACCESSORIES,
                        "Minimalist leather wallet.",
                        "Handcrafted genuine leather wallet with RFID protection. Slim design fits perfectly in any pocket.",
                        1500, 899);

                createProduct(productRepository, "Aviator Sunglasses", Category.ACCESSORIES,
                        "Classic gold frame aviators.",
                        "Timeless aviator sunglasses with UV400 protection. Adds a touch of cool to your sunny day look.",
                        2000, 1200);

                Product shoes = new Product();
                shoes.setName("Running Shoes");
                shoes.setCategory(Category.SHOES);
                shoes.setSmallDescription("High performance running shoes");
                shoes.setDescription("Lightweight and durable running shoes designed for marathon runners.");
                shoes.setSellingPrice(5000);
                shoes.setDiscountedPrice(3500);
                productRepository.save(shoes);

                System.out.println("✅ Products Seeded Successfully!");
            }
        };
    }

    private void createProduct(ProductRepository repo, String name, Category category, String smallDesc, String desc,
            int sellingPrice, int discountPrice) {
        Product p = new Product();
        p.setName(name);
        p.setCategory(category);
        p.setSmallDescription(smallDesc);
        p.setDescription(desc);
        p.setSellingPrice(sellingPrice);
        p.setDiscountedPrice(discountPrice);
        repo.save(p);
    }
}
