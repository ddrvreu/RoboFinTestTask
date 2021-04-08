package com.belukha.testtask.model;

public class CustomerDto {
    private String firstName;
    private String lastName;
    private String middleName;
    private String sex;
    private String country;
    private String region;
    private String city;
    private String street;
    private String house;
    private String flat;

    public CustomerDto() {
    }

    public CustomerDto(String firstName,
                       String lastName,
                       String middleName,
                       String sex,
                       String country,
                       String region,
                       String city,
                       String street,
                       String house,
                       String flat) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.sex = sex;
        this.country = country;
        this.region = region;
        this.city = city;
        this.street = street;
        this.house = house;
        this.flat = flat;
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

    public String getSex() {
        return sex;
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public void setHouse(String house) {
        this.house = house;
    }
}
