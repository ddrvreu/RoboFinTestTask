package com.belukha.testtask.repository;

import com.belukha.testtask.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByFirstNameAndLastName(String firstName, String LastName);
}
