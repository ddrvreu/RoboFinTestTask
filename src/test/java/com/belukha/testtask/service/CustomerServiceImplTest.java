package com.belukha.testtask.service;

import com.belukha.testtask.dao.AddressDao;
import com.belukha.testtask.dao.CustomerDao;
import com.belukha.testtask.model.Address;
import com.belukha.testtask.model.Customer;
import com.belukha.testtask.model.CustomerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerDao customerDao;
    @Mock
    private AddressDao addressDao;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;
    private Address address;
    private CustomerDto customerDto;

    private final String firstName = "firstName";
    private final String lastName = "lastName";

    @BeforeEach
    public void setup() {
        String middleName = "middleName";
        String sex = "MALE";
        String country = "country";
        String region = "region";
        String city = "city";
        String street = "street";
        String house = "house";
        String flat = "flat";
        address = new Address(country, region, city, street, house, flat);
        customer = new Customer(address, firstName, lastName, middleName, sex);
        customerDto = new CustomerDto(firstName, lastName, middleName, sex, country, region, city, street, house, flat);
    }

    @Test
    void addCustomerSuccessfully() {
        given(addressDao.findIdenticalAddress(isA(Address.class)))
                .willReturn(Optional.empty());
        doNothing().when(addressDao).addAddress(isA(Address.class));
        doNothing().when(customerDao).addCustomer(isA(Customer.class));

        customerService.addCustomer(customerDto);
    }

    @Test
    void addCustomerSuccessfullyIfPassedAddressIsExist() {
        given(addressDao.findIdenticalAddress(isA(Address.class)))
                .willReturn(Optional.of(address));
        doNothing().when(addressDao).addAddress(isA(Address.class));
        doNothing().when(customerDao).addCustomer(isA(Customer.class));

        customerService.addCustomer(customerDto);
    }

    @Test
    void addCustomerWhenCustomerDtoAllFieldsNull() {
        given(addressDao.findIdenticalAddress(isA(Address.class)))
                .willReturn(Optional.empty());

        CustomerDto customerDtoWithAllNullFields = new CustomerDto();

        assertThrows(IllegalStateException.class, () -> customerService.addCustomer(customerDtoWithAllNullFields));
    }

    @Test
    void findByFirstNameAndLastNameReturnsRightCustomer() {
        given(customerDao.findCustomerByFirstNameAndLastName(firstName, lastName))
                .willReturn(Optional.of(customer));

        Customer customerFromCustomerDao = customerService.findByFirstNameAndLastName(firstName, lastName);

        assertEquals(firstName, customerFromCustomerDao.getFirstName());
        assertEquals(lastName, customerFromCustomerDao.getLastName());
    }

    @Test
    void findByFirstNameAndLastNameThrows() {
        given(customerDao.findCustomerByFirstNameAndLastName(anyString(), anyString()))
                .willThrow(new NoSuchElementException());

        assertThrows(NoSuchElementException.class,
                () -> customerService.findByFirstNameAndLastName(firstName, lastName));
    }

    @Test
    void changeActualCustomerAddressSuccessfully() {
        given(addressDao.findIdenticalAddress(isA(Address.class)))
                .willReturn(Optional.empty());
        given(customerDao.findCustomerById(anyLong())).willReturn(Optional.of(customer));
        doNothing().when(addressDao).addAddress(isA(Address.class));
        doNothing().when(customerDao).addCustomer(isA(Customer.class));


        customerService.changeActualCustomerAddress(1L, address);
    }

    @Test
    void changeActualCustomerAddressSuccessfullyIfPassedAddressIsExist() {
        given(addressDao.findIdenticalAddress(isA(Address.class)))
                .willReturn(Optional.of(address));
        given(customerDao.findCustomerById(anyLong())).willReturn(Optional.of(customer));
        doNothing().when(addressDao).addAddress(isA(Address.class));
        doNothing().when(customerDao).addCustomer(isA(Customer.class));


        customerService.changeActualCustomerAddress(1L, address);
    }
}