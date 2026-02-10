package com.example.wearify.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.wearify.model.Product;
import com.example.wearify.model.enums.Category;
import com.example.wearify.repository.ProductRepository;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/products")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public String viewProducts(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "admin/product/view_product";
    }

    @GetMapping("/add")
    public String addProductForm() {
        return "admin/product/add_product";
    }

    @PostMapping("/add")
    public String addProduct(@RequestParam String name,
            @RequestParam Category category,
            @RequestParam String smallDescription,
            @RequestParam String description,
            @RequestParam Integer sellingPrice,
            @RequestParam Integer discountedPrice,
            @RequestParam(required = false) String imageUrl,
            @RequestParam MultipartFile productImage) throws IOException {
        Product product = new Product();
        product.setName(name);
        product.setCategory(category);
        product.setSmallDescription(smallDescription);
        product.setDescription(description);
        product.setSellingPrice(sellingPrice);
        product.setDiscountedPrice(discountedPrice);
        product.setImageUrl(imageUrl);

        if (!productImage.isEmpty()) {
            product.setProductImage(productImage.getBytes());
        }

        productRepository.save(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/update/{id}")
    public String updateProductForm(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id).orElseThrow();
        model.addAttribute("product", product);
        return "admin/product/update_product";
    }

    @PostMapping("/update")
    public String updateProduct(@RequestParam Long id,
            @RequestParam String name,
            @RequestParam Category category,
            @RequestParam String smallDescription,
            @RequestParam String description,
            @RequestParam Integer sellingPrice,
            @RequestParam Integer discountedPrice,
            @RequestParam(required = false) String imageUrl,
            @RequestParam MultipartFile productImage) throws IOException {
        Product product = productRepository.findById(id).orElseThrow();
        product.setName(name);
        product.setCategory(category);
        product.setSmallDescription(smallDescription);
        product.setDescription(description);
        product.setSellingPrice(sellingPrice);
        product.setDiscountedPrice(discountedPrice);
        product.setImageUrl(imageUrl);

        if (!productImage.isEmpty()) {
            product.setProductImage(productImage.getBytes());
        }

        productRepository.save(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/images/{id}")
    @ResponseBody
    public byte[] getProductImage(@PathVariable Long id) {
        Product product = productRepository.findById(id).orElseThrow();
        return product.getProductImage();
    }
}
