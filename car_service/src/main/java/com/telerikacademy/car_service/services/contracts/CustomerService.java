package com.telerikacademy.car_service.services.contracts;

import com.telerikacademy.car_service.models.Customer;
import com.telerikacademy.car_service.models.dto.customer.*;

import java.util.List;

public interface CustomerService {

    List<CustomerDto> getAllCustomers();

    Customer getCustomerByEmail(String customerEmail);

    Customer getCustomerById(Integer customerId);

    void addCustomer(CustomerCreateDto customerCreateDto);

    void editCustomerProfile(CustomerEditProfileDto customerEditProfileDto);

    void changeCustomerPassword(CustomerChangePasswordDto customerChangePasswordDto);

    void deleteCustomer(Integer customerId);

    void changeForgottenPassword(CustomerForgottenPasswordDto customerForgottenPasswordDto);
}
