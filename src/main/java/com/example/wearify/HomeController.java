package com.example.wearify;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.wearify.model.Product;
import com.example.wearify.model.enums.Category;
import com.example.wearify.repository.ProductRepository;

@Controller
public class HomeController {

    private final ProductRepository productRepository;

    public HomeController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/")
    public String index(jakarta.servlet.http.HttpSession session, Model model) {
        // Clear login notification attributes after they are displayed
        session.removeAttribute("loginSuccess");
        session.removeAttribute("loginError");

        // Fetch all products from database
        model.addAttribute("products", productRepository.findAll());
        return "index";
    }

    @GetMapping("/mens")
    public String mens(Model model) {
        model.addAttribute("products", productRepository.findByCategory(Category.MENS));
        return "mens";
    }

    @GetMapping("/womens")
    public String womens(Model model) {
        model.addAttribute("products", productRepository.findByCategory(Category.WOMENS));
        return "womens";
    }

    @GetMapping("/accessories")
    public String accessories(Model model) {
        model.addAttribute("products", productRepository.findByCategory(Category.ACCESSORIES));
        return "accessories";
    }

    @GetMapping("/shoes")
    public String shoes(Model model) {
        model.addAttribute("products", productRepository.findByCategory(Category.SHOES));
        return "shoes";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/images/{id}")
    @ResponseBody
    public byte[] getImage(@PathVariable long id) {
        Product product = productRepository.findById(id).orElseThrow();
        return product.getProductImage();
    }

    @GetMapping("/product-details/{id}")
    public String productDetails(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id).orElseThrow();
        model.addAttribute("product", product);
        return "product_details";
    }
}
