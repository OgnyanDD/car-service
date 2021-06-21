package com.telerikacademy.car_service.services;

import com.telerikacademy.car_service.models.Model;
import com.telerikacademy.car_service.repositories.CarModelRepository;
import com.telerikacademy.car_service.services.contracts.CarModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CarModelServiceImpl implements CarModelService {

    private CarModelRepository carModelRepository;

    @Autowired
    public CarModelServiceImpl(CarModelRepository carModelRepository) {
        this.carModelRepository = carModelRepository;
    }

    @Override
    public List<Model> listModels() {

        List<Model> modelList = carModelRepository.findAll();
        if(modelList.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No car models found!");
        }else {
            return modelList;
        }
    }

    @Override
    public List<Model> listModelsByMakerId(Integer makerId) {
        return carModelRepository.getModelsByMakerId(makerId);
    }
}
