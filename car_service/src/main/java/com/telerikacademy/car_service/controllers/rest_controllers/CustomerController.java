package com.telerikacademy.car_service.controllers.rest_controllers;

import com.telerikacademy.car_service.models.Customer;
import com.telerikacademy.car_service.models.dto.customer.*;
import com.telerikacademy.car_service.services.contracts.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/car-service")
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/admin/customers")
    Object getAllCustomers() {
        return new Object() {
            public final List<CustomerDto> data = customerService.getAllCustomers();
        };
    }

    @GetMapping("/admin/customer/email/{customerEmail}")
    Customer getCustomerByEmail(@PathVariable String customerEmail) {

        return customerService.getCustomerByEmail(customerEmail);
    }

    @GetMapping("/admin/customer/{customerId}")
    Customer getCustomerById(@PathVariable Integer customerId) {

        return customerService.getCustomerById(customerId);
    }

    @PostMapping("/register")
    public void addCustomer(@RequestBody CustomerCreateDto customerCreateDto) {

        customerService.addCustomer(customerCreateDto);
    }

    @PutMapping("/admin/customer/edit")
    public void editCustomer(@RequestBody CustomerEditProfileDto customerEditProfileDto) {

        customerService.editCustomerProfile(customerEditProfileDto);
    }

    @PutMapping("/admin/password")
    public void changeCustomerPassword(@RequestBody CustomerChangePasswordDto customerChangePasswordDto) {

        customerService.changeCustomerPassword(customerChangePasswordDto);
    }

    @DeleteMapping("/admin/customer/delete/{customerId}")
    public void deleteUser(@PathVariable Integer customerId) {

        customerService.deleteCustomer(customerId);
    }

    @PutMapping("/customer/new-password")
    public void resetCustomerPassword(@RequestBody CustomerForgottenPasswordDto customerForgottenPasswordDto) {

        customerService.changeForgottenPassword(customerForgottenPasswordDto);
    }
}
