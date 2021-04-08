package com.belukha.testtask.repository;

import com.belukha.testtask.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("""
           SELECT a FROM Address a 
           WHERE a.country = :country
           AND a.region = :region 
           AND a.city = :city 
           AND a.street = :street 
           AND a.house = :house 
           AND a.flat = :flat
           """
    )
    Optional<Address> findIdenticalAddress(@Param("country") String country,
                                  @Param("region") String region,
                                  @Param("city") String city,
                                  @Param("street") String street,
                                  @Param("house") String house,
                                  @Param("flat") String flat);
}
