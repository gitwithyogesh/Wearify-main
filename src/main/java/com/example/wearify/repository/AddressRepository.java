package com.example.wearify.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.wearify.model.Address;
import com.example.wearify.model.User;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByUser(User user);
}
