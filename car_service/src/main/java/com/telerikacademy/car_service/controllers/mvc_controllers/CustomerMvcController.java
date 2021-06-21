package com.telerikacademy.car_service.controllers.mvc_controllers;

import com.telerikacademy.car_service.models.Customer;
import com.telerikacademy.car_service.models.dto.customer.CustomerChangePasswordDto;
import com.telerikacademy.car_service.models.dto.customer.CustomerCreateDto;
import com.telerikacademy.car_service.models.dto.customer.CustomerEditProfileDto;
import com.telerikacademy.car_service.models.dto.customer.CustomerForgottenPasswordDto;
import com.telerikacademy.car_service.services.contracts.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("/car-service/")
public class CustomerMvcController {

    private CustomerService customerService;

    @Autowired
    public CustomerMvcController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("admin/customers")
    public String listUsers(Model model) {

        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("view", "customer/customers");

        return "base-layout";
    }

    @RequestMapping(value = {"/register", "/admin/register"}, method = RequestMethod.GET)
    public String register(Model model) {

        model.addAttribute("view", "customer/register");

        return "base-layout";
    }

    @RequestMapping(value = {"/register", "/admin/register"}, method = RequestMethod.POST)
    public String registerProcess(@ModelAttribute CustomerCreateDto customerCreateDto, HttpServletRequest request) {

        customerService.addCustomer(customerCreateDto);

        if (request.isUserInRole("ROLE_ADMIN")) {
            return "redirect:/car-service/admin/customers";
        }
        return "redirect:/car-service/login";
    }

    @RequestMapping(value = {"/customer/{id:\\d+}",
            "/admin/customer/{id:\\d+}", "/customer/profile"}, method = RequestMethod.GET)
    public String profile(Model model, @PathVariable Optional<Integer> id) {

        Customer customer;
        if (!id.isPresent()) {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            customer = customerService.getCustomerByEmail(authentication.getName());
        } else {
            customer = customerService.getCustomerById(id.get());
        }
        model.addAttribute("customer", customer);
        model.addAttribute("view", "customer/profile");

        return "base-layout";
    }

    @RequestMapping(value = {"/customer/{id:\\d+}/edit",
            "/admin/customer/{id:\\d+}/edit"}, method = RequestMethod.GET)
    public String editCustomerProfile(Model model, @PathVariable Integer id) {

        model.addAttribute("customer", customerService.getCustomerById(id));
        model.addAttribute("view", "customer/edit-profile");

        return "base-layout";
    }

    @RequestMapping(value = {"/customer/{id:\\d+}/edit", "/admin/customer/{id:\\d+}/edit"}, method = RequestMethod.POST)
    public String editCustomerProfileProcess(@ModelAttribute CustomerEditProfileDto customerEditProfileDto,
                                             @PathVariable Integer id, HttpServletRequest request) {

        Customer customer = customerService.getCustomerById(id);
        customerService.editCustomerProfile(customerEditProfileDto);

        if (request.isUserInRole("ROLE_ADMIN")) {
            return "redirect:/car-service/admin/customers";
        }
        return "redirect:/car-service/customer/" + customer.getId();
    }

    @RequestMapping(value = {"/customer/{id:\\d+}/password",
            "/admin/customer/{id:\\d+}/password"}, method = RequestMethod.GET)
    public String changeCustomerPassword(Model model, @PathVariable Integer id) {

        model.addAttribute("customer", customerService.getCustomerById(id));
        model.addAttribute("view", "customer/change-password");

        return "base-layout";
    }

    @RequestMapping(value = {"/customer/password", "/admin/customer/password"}, method = RequestMethod.POST)
    public String changeCustomerPasswordProcess(@ModelAttribute CustomerChangePasswordDto customerChangePasswordDto, HttpServletRequest request) {

        customerService.changeCustomerPassword(customerChangePasswordDto);

        if (request.isUserInRole("ROLE_ADMIN")) {
            return "redirect:/car-service/admin/customers";
        }
        return "redirect:/logout";
    }

    @GetMapping("reset-password")
    public String passwordReset(Model model) {

        model.addAttribute("view", "customer/reset-password");

        return "base-layout";
    }

    @PostMapping("reset-password")
    public String passwordResetProcess(@ModelAttribute CustomerForgottenPasswordDto customerForgottenPasswordDto) {

        customerService.changeForgottenPassword(customerForgottenPasswordDto);

        return "redirect:/car-service/login";
    }

    @PostMapping("/admin/customer/{id:\\d+}/delete")
    public String deleteProcess(@PathVariable Integer id) {

        customerService.deleteCustomer(id);

        return "redirect:/car-service/admin/customers";
    }

    @RequestMapping(value = {"/customer/{id:\\d+}/cars", "/admin/customer/{id:\\d+}/cars", "/customer/cars"}, method = RequestMethod.GET)
    public String getCustomerCars(Model model, @PathVariable Optional<Integer> id) {

        Customer customer;
        if (!id.isPresent()) {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            customer = customerService.getCustomerByEmail(authentication.getName());
        } else {
            customer = customerService.getCustomerById(id.get());
        }
        model.addAttribute("customer", customer);
        model.addAttribute("view", "customer_cars/cars");

        return "base-layout";
    }
}
