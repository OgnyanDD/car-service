package com.telerikacademy.car_service.controllers.mvc_controllers;

import com.telerikacademy.car_service.models.Customer;
import com.telerikacademy.car_service.models.CustomerCar;
import com.telerikacademy.car_service.models.dto.AddCarVisitDto;
import com.telerikacademy.car_service.services.contracts.AutoServicesService;
import com.telerikacademy.car_service.services.contracts.CarsVisitsService;
import com.telerikacademy.car_service.services.contracts.CustomerService;
import com.telerikacademy.car_service.services.contracts.CustomersCarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/car-service/")
public class CarVisitsMvcController {

    private CarsVisitsService carsVisitsService;
    private CustomerService customerService;
    private CustomersCarsService customersCarsService;
    private AutoServicesService autoServicesService;

    @Autowired
    public CarVisitsMvcController(CarsVisitsService carsVisitsService,
                                  CustomerService customerService,
                                  CustomersCarsService customersCarsService,
                                  AutoServicesService autoServicesService) {
        this.carsVisitsService = carsVisitsService;
        this.customerService = customerService;
        this.customersCarsService = customersCarsService;
        this.autoServicesService= autoServicesService;
    }

    @RequestMapping(value = {"/customer/{customerId:\\d+}/car/{carId:\\d+}/visits/{visitId:\\d+}",
            "/admin/customer/{customerId:\\d+}/car/{carId:\\d+}/visits/{visitId:\\d+}"},
            method = RequestMethod.GET)
    public String CarVisit(Model model,
                           @PathVariable Integer customerId,
                           @PathVariable Integer carId,
                           @PathVariable Integer visitId) {
        Customer customer = customerService.getCustomerById(customerId);
        CustomerCar customerCar = customersCarsService.findCarById(carId);
        model.addAttribute("customer", customer);
        model.addAttribute("car", customerCar);
        model.addAttribute("visit", carsVisitsService.getCarVisitDtoById(visitId));
        model.addAttribute("services", carsVisitsService.getCarVisitDtoById(visitId).getServices());
        model.addAttribute("view", "car_visits/car-visit");

        return "base-layout";
    }

    @RequestMapping(value = {"/customer/{customerId:\\d+}/car/{carId:\\d+}/visits",
            "/admin/customer/{customerId:\\d+}/car/{carId:\\d+}/visits"}, method = RequestMethod.GET)
    public String getCustomerCars(Model model, @PathVariable Integer customerId, @PathVariable Integer carId) {

        Customer customer = customerService.getCustomerById(customerId);
        CustomerCar customerCar = customersCarsService.findCarById(carId);
        model.addAttribute("customer", customer);
        model.addAttribute("car", customerCar);
        model.addAttribute("view", "car_visits/visits");

        return "base-layout";
    }

    @GetMapping("admin/customer/{id}/car/{carId}/new-visit")
    public String addNewVisit(Model model, @PathVariable Integer id, @PathVariable Integer carId) {
        model.addAttribute("customer", customerService.getCustomerById(id));
        model.addAttribute("services", autoServicesService.showAllAutoServices());
        model.addAttribute("car", customersCarsService.customerCarDto(carId));
        model.addAttribute("view", "car_visits/new-visit");

        return "base-layout";
    }

    @PostMapping("admin/customer/{customerId}/car/{carId}/new-visit")
    public String addVisit(@ModelAttribute AddCarVisitDto visitDto,
                           @PathVariable Integer customerId, @PathVariable Integer carId) {

        Customer customer = customerService.getCustomerById(customerId);
        CustomerCar customerCar = customersCarsService.findCarById(carId);

        carsVisitsService.addCarVisit(visitDto);
        return "redirect:/car-service/admin/customer/" + customer.getId() + "/car/" + customerCar.getId() + "/visits";
    }
}
