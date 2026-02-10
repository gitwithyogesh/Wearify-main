package com.example.wearify.model;

import com.example.wearify.model.enums.Category;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String smallDescription;

    @Lob
    private String description;

    private Integer sellingPrice;
    private Integer discountedPrice;

    @Lob
    @com.fasterxml.jackson.annotation.JsonIgnore
    private byte[] productImage;

    private String imageUrl;
}
