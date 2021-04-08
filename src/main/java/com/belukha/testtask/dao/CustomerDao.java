package com.belukha.testtask.dao;

import com.belukha.testtask.model.Customer;

import java.util.Optional;

public interface CustomerDao {

    void addCustomer(Customer customer);

    Optional<Customer> findCustomerByFirstNameAndLastName(String firstName, String lastName);

    Optional<Customer> findCustomerById(Long id);
}
