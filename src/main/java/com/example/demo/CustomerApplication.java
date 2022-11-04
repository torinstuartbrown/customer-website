package com.example.demo;

import com.example.demo.models.User;
import com.example.demo.repos.UserRepository;
import com.example.demo.security.Role;
import com.example.demo.security.RoleRepository;
import com.example.demo.services.CustomerService;
import com.example.demo.services.InstrumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.Collections;

@SpringBootApplication
public class CustomerApplication {
    //implements CommandLineRunner {
    @Autowired
    private CustomerService customerService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    InstrumentService instrumentService;

    // The main method is defined here which will start your application
    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class);
    }

    @Bean
    //?
    @Transactional
    public CommandLineRunner loadInitialRoleData(RoleRepository roleRepository, UserRepository userRepository) {
        return (args) -> {
            Role userRole = new Role(Role.Roles.ROLE_USER);
            Role adminRole = new Role(Role.Roles.ROLE_ADMIN);
            if (roleRepository.findAll().isEmpty()) {
                roleRepository.save(userRole);
                adminRole = roleRepository.save(adminRole);
                userRepository.save(User.builder()
                        .firstName("Admin")
                        .lastName("User")
                        .password(passwordEncoder.encode("123"))
                        .authorities(Collections.singletonList(adminRole))
                        .email("admin@gmail.com")
                        .build());
            }
        };
    }

}
