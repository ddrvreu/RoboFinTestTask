package com.belukha.testtask.controller;

import com.belukha.testtask.model.Address;
import com.belukha.testtask.model.Customer;
import com.belukha.testtask.model.CustomerDto;
import com.belukha.testtask.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {
    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private Customer customer;
    private Address address;
    private CustomerDto customerDto;
    private ObjectMapper mapper;

    private final String firstName = "firstName";
    private final String lastName = "lastName";
    private final String middleName = "middleName";
    private final String sex = "MALE";

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(customerController)
                .build();
        customer = new Customer(null, firstName, lastName, middleName, sex);
        String country = "country";
        String region = "region";
        String city = "city";
        String street = "street";
        String house = "house";
        String flat = "flat";
        address = new Address(country, region, city, street, house, flat);
        customerDto = new CustomerDto(firstName, lastName, middleName, sex, country, region, city, street, house, flat);
        mapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Response should contain right json object and HTTP OK status")
    public void getCustomerByFirstNameAndLastReturnsRightObject() throws Exception {
        given(customerService.findByFirstNameAndLastName(customer.getFirstName(), customer.getLastName()))
                .willReturn(customer);

        String url = "/api/v1/customers/?firstName=" + firstName +
                     "&lastName=" +
                     lastName;
        this.mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").doesNotExist())
                .andExpect(jsonPath("firstName").value(firstName))
                .andExpect(jsonPath("lastName").value(lastName))
                .andExpect(jsonPath("middleName").value(middleName))
                .andExpect(jsonPath("customerSex").value(sex))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Response should contain HTTP Bad Request status")
    public void getCustomerByFirstNameAndLastNameReturnsRightHttpStatus() throws Exception {
        String url = "/api/v1/customers/?firstName=" + firstName;
        this.mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Response should contain HTTP Not found status")
    public void getCustomerByFirstNameAndLastNameReturnsNotFoundStatus() throws Exception {
        given(customerService.findByFirstNameAndLastName(anyString(), anyString()))
                .willThrow(new NoSuchElementException());

        this.mockMvc.perform(get("/api/v1/customers/?firstName=A&lastName=B"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Json request body must be mapped into CustomerDto.class")
    public void addCustomerShouldWorkSuccessfully() throws Exception {
        doNothing().when(customerService).addCustomer(isA(CustomerDto.class));

        this.mockMvc.perform(post("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(customerDto)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Json response body should contain Http Bad request status")
    public void addCustomerWithWrongJsonFormatContent() throws Exception {
        String wrongJsonFormatString = "<10101>";

        this.mockMvc.perform(post("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(wrongJsonFormatString))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Json response must have error message when CustomerService throw IllegalStateException")
    public void addCustomerWithWrongParameterValue() throws Exception {
        doThrow(new IllegalStateException()).when(customerService).addCustomer(isA(CustomerDto.class));

        this.mockMvc.perform(post("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(customerDto)))
                .andDo(print())
                .andExpect(jsonPath("message").isNotEmpty())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Response should contain Http Ok status")
    public void changeActualCustomerAddressSuccessfully() throws Exception {
        doNothing().when(customerService).changeActualCustomerAddress(eq(1L), isA(Address.class));

        this.mockMvc.perform(patch("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(address)))
                .andDo(print())
                .andExpect(jsonPath("message").isNotEmpty())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Response should contain Http Not Found status")
    public void changeActualCustomerAddressReturnNotFoundStatusInResponse() throws Exception {
        doThrow(new NoSuchElementException())
                .when(customerService).changeActualCustomerAddress(eq(100L), isA(Address.class));

        this.mockMvc.perform(patch("/api/v1/customers/100")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(address)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}