package com.belukha.testtask.service;

import com.belukha.testtask.model.Address;
import com.belukha.testtask.model.Customer;
import com.belukha.testtask.model.CustomerDto;

public interface CustomerService {

    void addCustomer(CustomerDto customerDto);

    Customer findByFirstNameAndLastName(String firstName, String lastName);

    void changeActualCustomerAddress(Long customerId, Address address);
}
