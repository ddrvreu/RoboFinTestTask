package com.belukha.testtask.model;

import javax.persistence.*;
import java.util.Locale;

@Entity
@Table
public class Customer {
    public enum CustomerSex {
        MALE,
        FEMALE
    }

    @Id
    @SequenceGenerator(name = "customer_sequence",
            sequenceName = "customer_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "customer_sequence")
    @Column(nullable = false)
    private Long id;

    private String firstName;
    private String lastName;
    private String middleName;
    private CustomerSex customerSex;

    @ManyToOne
    @JoinColumn(name = "registered_address_id",
            nullable = false)
    private Address registeredAddressId;

    @ManyToOne
    @JoinColumn(name = "actual_address_id",
            nullable = false)
    private Address actualAddressId;

    public Customer() {
    }

    public Customer(
            Address address,
            String firstName,
            String lastName,
            String middleName,
            String customerSex) {
        this.registeredAddressId = address;
        this.actualAddressId = address;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.customerSex = recognizeCustomerSex(customerSex);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setCustomerSex(String customerSex) {
        this.customerSex = recognizeCustomerSex(customerSex);
    }

    public void setActualAddressId(Address actualAddressId) {
        this.actualAddressId = actualAddressId;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public CustomerSex getCustomerSex() {
        return customerSex;
    }

    @Override
    public String toString() {
        return "Customer{" +
               "id=" + id +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", middleName='" + middleName + '\'' +
               ", customerSex=" + customerSex +
               ", registeredAddressId=" + registeredAddressId +
               ", actualAddressId=" + actualAddressId +
               '}';
    }

    private CustomerSex recognizeCustomerSex(String customerSex) {
        if(customerSex == null) {
            throw new IllegalStateException("Customer sex must be not null");
        }
        return switch (customerSex.toUpperCase(Locale.ROOT)) {
            case "MALE" -> CustomerSex.MALE;
            case "FEMALE" -> CustomerSex.FEMALE;
            default -> throw new IllegalStateException("Unexpected customer sex value: " + customerSex);
        };
    }
}

