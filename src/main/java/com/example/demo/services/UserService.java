package com.example.demo.services;

import com.example.demo.models.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User saveUser(User user);

    User getUser(String email);

    User updateUser(String userName, User updatedUser);

}
