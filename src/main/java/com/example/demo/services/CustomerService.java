package com.example.demo.services;

import com.example.demo.models.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();

    Customer saveCustomer(Customer customer);

    Customer getCustomer(Long id);

    Customer getCustomerByEmail(String email);

    void deleteCustomer(Long id);

    List<Customer> saveAllCustomer(List<Customer> customerList);

    List<Customer> getAllAvailableCustomers();
}
