package com.telerikacademy.car_service.controllers.mvc_controllers;

import com.telerikacademy.car_service.models.Customer;
import com.telerikacademy.car_service.models.CustomerCar;
import com.telerikacademy.car_service.models.dto.AddOrEditCustomerCarDto;
import com.telerikacademy.car_service.services.contracts.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/car-service/admin/")
public class CustomerCarAdminMvcController {

    private CarMakerService makerService;
    private CarModelService carModelService;
    private CustomerService customerService;
    private CustomersCarsService carsService;

    @Autowired
    public CustomerCarAdminMvcController(CustomersCarsService carsService,
                                         CarMakerService makerService,
                                         CarModelService carModelService,
                                         CustomerService customerService) {
        this.carsService = carsService;
        this.makerService = makerService;
        this.carModelService = carModelService;
        this.customerService = customerService;
    }

    @GetMapping("/customer/{customerId}/new-car")
    public String addNewCustomerCar(Model model, @PathVariable Integer customerId) {

        Customer customer = customerService.getCustomerById(customerId);

        model.addAttribute("customer", customer);
        model.addAttribute("makers", makerService.makerList());
        model.addAttribute("view", "customer_cars/add-edit-car");
        return "base-layout";
    }

    @PostMapping("/customer/new-car")
    public String addNewCustomerCarProcess(@ModelAttribute AddOrEditCustomerCarDto carDto) {

        carsService.addNewCustomerCar(carDto);
        return "redirect:/car-service/customer/" + carDto.getCustomerId() + "/cars";
    }

    @GetMapping("/customer/{customerId}/car/{carId}/edit")
    public String editCustomerCar(Model model, @PathVariable Integer customerId, @PathVariable Integer carId) {

        Customer customer = customerService.getCustomerById(customerId);
        CustomerCar customerCar = customer.getCustomerCars()
                .stream()
                .filter(c-> c.getId().equals(carId))
                .findFirst()
                .get();

        List<com.telerikacademy.car_service.models.Model> modelList = carModelService
                .listModelsByMakerId(customerCar.getYear().getModel().getMaker().getId());

        model.addAttribute("customer", customer);
        model.addAttribute("customerCar", customerCar);
        model.addAttribute("carModels", modelList);

        model.addAttribute("selectedCarMakerId", customerCar.getYear().getModel().getMaker().getId());
        model.addAttribute("selectedCarModelId", customerCar.getYear().getModel().getId());
        model.addAttribute("selectedModelYearId", customerCar.getYear().getId());

        model.addAttribute("makers", makerService.makerList());
        model.addAttribute("method", "edit");
        model.addAttribute("view", "customer_cars/add-edit-car");

        return "base-layout";
    }

    @PostMapping("/customer/edit-car")
    public String editCustomerCarProcess(@ModelAttribute AddOrEditCustomerCarDto carDto) {

        carsService.editCustomerCar(carDto);
        return "redirect:/car-service/customer/" + carDto.getCustomerId() + "/cars";
    }

    @PostMapping("/customer/{customerId:\\d+}/car/{carId:\\d+}/delete")
    public String deleteProcess(@PathVariable Integer customerId, @PathVariable Integer carId) {

        Customer customer = customerService.getCustomerById(customerId);
        carsService.deleteCustomerCar(carId);

        return "redirect:/car-service/admin/customer/" + customer.getId() + "/cars";
    }
}
