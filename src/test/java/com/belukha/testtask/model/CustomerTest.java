package com.belukha.testtask.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void setCustomerSexStringParameterShouldBeMappedCorrectly() {
        Customer firstCustomer = new Customer();
        Customer secondCustomer = new Customer();
        Customer thirdCustomer = new Customer();
        Customer forthCustomer = new Customer();

        firstCustomer.setCustomerSex("MALE");
        secondCustomer.setCustomerSex("male");
        thirdCustomer.setCustomerSex("FEMALE");
        forthCustomer.setCustomerSex("female");

        assertEquals(Customer.CustomerSex.MALE, firstCustomer.getCustomerSex());
        assertEquals(Customer.CustomerSex.MALE, secondCustomer.getCustomerSex());
        assertEquals(Customer.CustomerSex.FEMALE, thirdCustomer.getCustomerSex());
        assertEquals(Customer.CustomerSex.FEMALE, forthCustomer.getCustomerSex());
    }
}