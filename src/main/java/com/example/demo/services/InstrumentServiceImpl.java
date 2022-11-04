package com.example.demo.services;

import com.example.demo.models.Customer;
import com.example.demo.models.Instrument;
import com.example.demo.repos.CustomerRepository;
import com.example.demo.repos.InstrumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional

public class InstrumentServiceImpl implements InstrumentService{

    @Autowired
    public CustomerRepository customerRepository;
    @Autowired
    public InstrumentRepository instrumentRepository;

    @Override
    public List<Instrument> getAllInstruments() {

        List<Instrument> instrumentList = instrumentRepository.findAll();
        for (Instrument i : instrumentList){
            //set the assigned boolean to true or false based on if this instrument ID is associated with a customer
            Customer customer = customerRepository.findByInstrument(i);
            if(customer != null){
                i.setCustomer(customer);
            }
        }
        return instrumentList;
    }

    @Override
    public List<Instrument> getAllAvailableInstruments() {
        return instrumentRepository.findByCustomerIsNull();
    }

    @Override
    @Transactional
    public Instrument saveInstrument(Instrument instrument) {
        if (instrument.getCustomerId() != null){
            Optional<Customer> customerOp = customerRepository.findById(instrument.getCustomerId());
            if(customerOp.get() != null){
                Customer customer = customerOp.get();
                customer.setInstrument(instrument);
                customerRepository.save(customer);
                instrument.setCustomer(customer);
            }

        } else{
            instrument.setCustomer(null);
//            Customer customer = customerRepository.findByInstrument(instrument);
//            if(customer == null){
//                customer.setInstrument(null);
//                customerRepository.save(customer);
//            }
        }
        return instrumentRepository.save(instrument);
    }

    @Override
    public Instrument getInstrument(Long id) {
        return instrumentRepository.findById(id)
                .orElse(null);
    }

    @Override
    @Transactional
    public void deleteInstrument(Long id) {
        Customer customer = instrumentRepository.findById(id).get().customer;
            instrumentRepository.deleteById(id);
        }


    @Override
    @Transactional
    public List<Instrument> saveAllInstruments(List<Instrument> instrumentsList) {
        return instrumentRepository.saveAll(instrumentsList);
    }
}
