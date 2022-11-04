package com.example.demo.repos;

import com.example.demo.models.Customer;
import com.example.demo.models.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByInstrument(Instrument i);

    List<Customer> findByInstrumentIsNull();

    Customer findByEmailAddress(String email);
}
