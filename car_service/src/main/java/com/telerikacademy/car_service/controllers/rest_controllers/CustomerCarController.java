package com.telerikacademy.car_service.controllers.rest_controllers;

import com.telerikacademy.car_service.models.dto.*;
import com.telerikacademy.car_service.services.contracts.CustomersCarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customer-cars/")
public class CustomerCarController {

    private CustomersCarsService carsService;

    @Autowired
    public CustomerCarController(CustomersCarsService carsService) {
        this.carsService = carsService;
    }

    @GetMapping("admin")
    public List<CustomerCarInfoDto> showAllCustomersCars() {
        return carsService.showAllCars();
    }

    @GetMapping("/{id}")
    public CustomerCarDto customerCarDto(@PathVariable Integer id) {
        return carsService.customerCarDto(id);
    }

    @GetMapping("admin/{registration}")
    public CustomerCarDto showCarByRegistrationNumber(@PathVariable String registration){
        return carsService.findByRegistration(registration);
    }

    @PostMapping("admin")
    public void addNewCar(@RequestBody AddOrEditCustomerCarDto addCustomerCarDto) {
        carsService.addNewCustomerCar(addCustomerCarDto);
    }

    @PutMapping("admin")
    public void editCustomerCar(@RequestBody AddOrEditCustomerCarDto editCustomerCarDto) {
        carsService.editCustomerCar(editCustomerCarDto);
    }

    @DeleteMapping("admin/{id}")
    public void deleteCustomerCar(@PathVariable Integer id){
        carsService.deleteCustomerCar(id);
    }

    @GetMapping("customer/{id}/cars")
    Object getAllCustomerCars(@PathVariable Integer id){
        return new Object(){
            public final List<CustomerCarDto> data = carsService.showAllCustomerCars(id);
        };
    }
}
