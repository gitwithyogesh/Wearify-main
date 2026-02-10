package com.example.wearify.model;

import com.example.wearify.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;

    private LocalDateTime orderAt;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_status")
    private String paymentStatus;

    public Order() {
        this.quantity = 1;
        this.orderAt = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
        this.paymentStatus = "PENDING";
    }
}
