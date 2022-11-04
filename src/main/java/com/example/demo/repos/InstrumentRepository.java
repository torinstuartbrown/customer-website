package com.example.demo.repos;

import com.example.demo.models.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstrumentRepository extends JpaRepository<Instrument, Long> {

    public List<Instrument> findByCustomerIsNull();
}

