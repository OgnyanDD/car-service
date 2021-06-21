package com.telerikacademy.car_service.controllers.rest_controllers;


import com.telerikacademy.car_service.models.dto.autoservice.AddAutoServiceDto;
import com.telerikacademy.car_service.models.dto.autoservice.AutoServiceDto;
import com.telerikacademy.car_service.services.contracts.AutoServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auto-services/admin")
public class AutoServiceController {

    private AutoServicesService service;

    @Autowired
    public AutoServiceController(AutoServicesService service) {
        this.service = service;

    }

    @GetMapping
    Object showAllAutoServices() {
        return new Object() {
            public final List<AutoServiceDto> data = service.showAllAutoServices();
        };
    }

    @GetMapping("/{id}")
    public AutoServiceDto autoServiceById(@PathVariable Integer id) {

        return service.getAutoServiceById(id);
    }

    @GetMapping("/auto-service-category/{id}")
    public List<AutoServiceDto> getAutoServiceByCategoryId(@PathVariable Integer id) {

        return service.showAutoServiceByCategory(id);
    }

    @PostMapping("/new-service")
    public void addNewService(@RequestBody AddAutoServiceDto autoServiceDto) {

        service.addNewAutoService(autoServiceDto);
    }

    @PutMapping("/edit-service")
    public void editAutoService(@RequestBody AddAutoServiceDto autoServiceDto) {

        service.editAutoService(autoServiceDto);
    }

    @DeleteMapping("/{id}")
    public void deleteAutoService(@PathVariable Integer id) {

        service.deleteAutoService(id);
    }

}
