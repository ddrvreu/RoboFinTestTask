package com.belukha.testtask.model;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table
public class Address {
    @Id
    @SequenceGenerator(
            name = "address_sequence",
            sequenceName = "address_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "address_sequence"
    )
    @Column(nullable = false)
    private Long id;
    private String country;
    private String region;
    private String city;
    private String street;
    private String house;
    private String flat;
    @Column(name = "created", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime created;
    @Column(name = "modified", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime modified;
    @OneToMany(mappedBy = "registeredAddressId",
            cascade = CascadeType.ALL)
    private Set<Customer> registeredCustomers;
    @OneToMany(mappedBy = "actualAddressId",
            cascade = CascadeType.ALL)
    private Set<Customer> actualAddressOfCustomers;

    public Address() {
    }

    public Address(String country,
                   String region,
                   String city,
                   String street,
                   String house,
                   String flat) {
        this.country = country;
        this.region = region;
        this.city = city;
        this.street = street;
        this.house = house;
        this.flat = flat;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public void setCreated(OffsetDateTime created) {
        this.created = created;
    }

    public void setModified(OffsetDateTime modified) {
        this.modified = modified;
    }

    public Long getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getHouse() {
        return house;
    }

    public String getFlat() {
        return flat;
    }

    public OffsetDateTime getCreated() {
        return created;
    }

    public OffsetDateTime getModified() {
        return modified;
    }

    public Set<Customer> getRegisteredCustomers() {
        return registeredCustomers;
    }

    public Set<Customer> getActualAddressOfCustomers() {
        return actualAddressOfCustomers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(country, address.country) && Objects.equals(region, address.region)
               && Objects.equals(city, address.city) && Objects.equals(street, address.street)
               && Objects.equals(house, address.house) && Objects.equals(flat, address.flat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, region, city, street, house, flat);
    }

    @Override
    public String toString() {
        return "Address{" +
               "country='" + country + '\'' +
               ", region='" + region + '\'' +
               ", city='" + city + '\'' +
               ", street='" + street + '\'' +
               ", house='" + house + '\'' +
               ", flat='" + flat + '\'' +
               '}';
    }
}

