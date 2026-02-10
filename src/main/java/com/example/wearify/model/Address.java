package com.example.wearify.model;

import com.example.wearify.model.enums.State;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String area;
    private String city;

    @Enumerated(EnumType.STRING)
    private State state;

    private Integer pincode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
