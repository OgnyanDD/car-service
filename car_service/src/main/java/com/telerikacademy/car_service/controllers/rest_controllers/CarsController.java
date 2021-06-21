package com.telerikacademy.car_service.controllers.rest_controllers;

import com.telerikacademy.car_service.models.Model;
import com.telerikacademy.car_service.models.dto.ManufactureYearDto;
import com.telerikacademy.car_service.services.contracts.CarModelService;
import com.telerikacademy.car_service.services.contracts.ManufactureYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cars/admin")
public class CarsController {

    private CarModelService carModelService;
    private ManufactureYearService manufactureYearService;

    @Autowired
    public CarsController(CarModelService carModelService, ManufactureYearService manufactureYearService) {
        this.carModelService = carModelService;
        this.manufactureYearService = manufactureYearService;
    }

    @GetMapping("/maker-models/{makerId}")
    public List<Model> getMakerModels(@PathVariable Integer makerId) {
        return carModelService.listModelsByMakerId(makerId);
    }

    @GetMapping("/model-years/{modelId}")
    public List<ManufactureYearDto> getModelYears(@PathVariable Integer modelId) {
        return manufactureYearService.getModelYearsByModelId(modelId);
    }
}
