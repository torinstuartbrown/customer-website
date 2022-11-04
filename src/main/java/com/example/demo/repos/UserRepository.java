package com.example.demo.repos;

import com.example.demo.models.Customer;
import com.example.demo.models.Instrument;
import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

        User findByEmail(String email);




}
