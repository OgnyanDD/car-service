package com.telerikacademy.car_service.services.contracts;

import com.telerikacademy.car_service.models.Model;

import java.util.List;

public interface CarModelService {
    List<Model> listModels();

    List<Model> listModelsByMakerId(Integer makerId);
}
