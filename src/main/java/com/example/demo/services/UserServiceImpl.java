package com.example.demo.services;

import com.example.demo.models.Customer;
import com.example.demo.models.Instrument;
import com.example.demo.models.User;
import com.example.demo.repos.UserRepository;
import com.example.demo.security.Role;
import com.example.demo.security.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    final UserRepository userRepository;

    @Autowired
    final RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {

        Role role = roleRepository.findByRole(Role.Roles.ROLE_USER);
        user.addRole(role);
        user.setPassword(encoder.encode(user.getPassword()));
        User newUser = userRepository.save(user);
        return newUser;
    }

    @Override
    public User updateUser(String userName, User updatedUser){
        User user = getUser(userName);
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setEmail(updatedUser.getEmail());
        return userRepository.save(user);
    }

    @Override
    public User getUser(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            //throw no user found exception
        }
        return user;
    }

    // The save function uses an INSERT query in the DB.
    @Transactional
    public User saveCustomer(User user) {
        return userRepository.save(user);
    }
}
