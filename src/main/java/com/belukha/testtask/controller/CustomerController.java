package com.belukha.testtask.controller;

import com.belukha.testtask.message.Messages;
import com.belukha.testtask.model.Address;
import com.belukha.testtask.model.Customer;
import com.belukha.testtask.model.CustomerDto;
import com.belukha.testtask.model.MessageDto;
import com.belukha.testtask.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<Customer> getCustomerByFirstNameAndLastName(@RequestParam String firstName,
                                                                      @RequestParam String lastName) {
        try {
            return new ResponseEntity<>(
                    customerService.findByFirstNameAndLastName(firstName, lastName), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    @ResponseBody()
    public ResponseEntity<MessageDto> addCustomer(@RequestBody CustomerDto customerDto) {
        try {
            customerService.addCustomer(customerDto);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(
                    new MessageDto(Messages.WRONG_CUSTOMER_SEX_VALUE +
                                   Arrays.toString(Customer.CustomerSex.values())), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(
                new MessageDto(Messages.CUSTOMER_WAS_CREATED),
                HttpStatus.CREATED);
    }

    @PatchMapping("/{customerId}")
    @ResponseBody
    public ResponseEntity<MessageDto> changeActualCustomerAddress(@PathVariable Long customerId,
                                                                  @RequestBody Address address) {
        try {
            customerService.changeActualCustomerAddress(customerId, address);
            return new ResponseEntity<>(
                    new MessageDto(Messages.CUSTOMER_ADDRESS_WAS_CHANGED), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

