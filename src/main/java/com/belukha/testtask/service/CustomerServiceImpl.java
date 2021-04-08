package com.belukha.testtask.service;

import com.belukha.testtask.dao.AddressDao;
import com.belukha.testtask.dao.CustomerDao;
import com.belukha.testtask.model.Address;
import com.belukha.testtask.model.Customer;
import com.belukha.testtask.model.CustomerDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{
    private static final Logger log = LogManager.getLogger("CustomerServiceImpl");
    private static final String TIME_ZONE_ID = "Europe/Moscow";
    private final CustomerDao customerDao;
    private final AddressDao addressDao;

    @Autowired
    public CustomerServiceImpl(CustomerDao customerDao, AddressDao addressDao) {
        this.customerDao = customerDao;
        this.addressDao = addressDao;
    }

    @Override
    @Transactional
    public void addCustomer(CustomerDto customerDto) {
        Address address = getAddress(customerDto);
        address = getAddressFromDaoIfItExist(address);
        Customer customer = getCustomer(customerDto, address);

        addressDao.addAddress(address);
        customerDao.addCustomer(customer);

        log.info("Customer: {} was added", customer.toString());
    }

    @Override
    public Customer findByFirstNameAndLastName(String firstName, String lastName) {
        return customerDao.findCustomerByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    @Transactional
    public void changeActualCustomerAddress(Long customerId, Address address) {
        Customer customer = customerDao.findCustomerById(customerId)
                .orElseThrow(NoSuchElementException::new);
        address = getAddressFromDaoIfItExist(address);
        customer.setActualAddressId(address);

        addressDao.addAddress(address);
        customerDao.addCustomer(customer);

        log.info("Customer with id : {}, now has following actualAddress : {}", customerId, address.toString());
    }

    private Address getAddressFromDaoIfItExist(Address address) {
        Optional<Address> addressOptional = addressDao.findIdenticalAddress(address);

        OffsetDateTime currantTime = OffsetDateTime.now(ZoneId.of(TIME_ZONE_ID));
        if (addressOptional.isPresent()) {
            address = addressOptional.get();
        } else {
            address.setCreated(currantTime);
        }
        address.setModified(currantTime);
        return address;
    }

    private Customer getCustomer(CustomerDto customerDto, Address address) {
        return new Customer(address,
                customerDto.getFirstName(),
                customerDto.getLastName(),
                customerDto.getMiddleName(),
                customerDto.getSex());
    }

    private Address getAddress(CustomerDto customerDto) {
        return new Address(customerDto.getCountry(),
                customerDto.getRegion(),
                customerDto.getCity(),
                customerDto.getStreet(),
                customerDto.getHouse(),
                customerDto.getFlat());
    }
}
