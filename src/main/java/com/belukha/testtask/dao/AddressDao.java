package com.belukha.testtask.dao;

import com.belukha.testtask.model.Address;

import java.util.Optional;

public interface AddressDao {

    void addAddress(Address address);

    Optional<Address> findIdenticalAddress(Address address);
}
