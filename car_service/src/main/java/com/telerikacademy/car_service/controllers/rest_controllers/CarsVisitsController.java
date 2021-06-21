package com.telerikacademy.car_service.controllers.rest_controllers;

import com.telerikacademy.car_service.models.CarVisit;
import com.telerikacademy.car_service.models.dto.*;
import com.telerikacademy.car_service.services.contracts.CarsVisitsService;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/cars-visits")
public class CarsVisitsController {

    private CarsVisitsService carsVisitsService;

    @Autowired
    public CarsVisitsController(CarsVisitsService carsVisitsService) {
        this.carsVisitsService = carsVisitsService;
    }

    @GetMapping("/admin/all")
    public List<CarVisitsInfoDto> carsVisitsList() {

        return carsVisitsService.getAllCarsVisits();
    }

    @GetMapping("/{id}")
    public CarVisitDto findVisitById(@PathVariable int id) {

        return carsVisitsService.getCarVisitDtoById(id);
    }

    @PostMapping("admin/new-visit")
    public void addCarVisit(@RequestBody AddCarVisitDto addCarVisitDto) {

        carsVisitsService.addCarVisit(addCarVisitDto);

    }

    @PutMapping("admin/")
    public void editCarVisit(@RequestBody EditCarVisitDto editCarVisitDto) {

        carsVisitsService.editCarVisits(editCarVisitDto);

    }

    @GetMapping("customer-visits/{id}")
    Object getAllCustomerVisits(@PathVariable Integer id){
        return new Object(){
            public final List<CarVisitsInfoDto> data = carsVisitsService.singleCarVisits(id);
        };
    }
}
