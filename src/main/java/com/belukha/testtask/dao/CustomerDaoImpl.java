package com.belukha.testtask.dao;

import com.belukha.testtask.model.Customer;
import com.belukha.testtask.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class CustomerDaoImpl implements CustomerDao {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerDaoImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    @Transactional
    public Optional<Customer> findCustomerByFirstNameAndLastName(String firstName, String lastName) {
        return Optional.ofNullable(customerRepository.findByFirstNameAndLastName(firstName, lastName));
    }

    @Override
    public Optional<Customer> findCustomerById(Long id) {
        return customerRepository.findById(id);
    }
}
