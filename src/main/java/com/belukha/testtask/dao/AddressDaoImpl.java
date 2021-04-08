package com.belukha.testtask.dao;

import com.belukha.testtask.model.Address;
import com.belukha.testtask.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AddressDaoImpl implements AddressDao {
    private final AddressRepository addressRepository;

    @Autowired
    public AddressDaoImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public void addAddress(Address address) {
        addressRepository.save(address);
    }

    @Override
    public Optional<Address> findIdenticalAddress(Address address) {
        return addressRepository.findIdenticalAddress(address.getCountry(),
                address.getRegion(),
                address.getCity(),
                address.getStreet(),
                address.getHouse(),
                address.getFlat());
    }
}
