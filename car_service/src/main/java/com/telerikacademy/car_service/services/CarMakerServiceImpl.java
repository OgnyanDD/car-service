package com.telerikacademy.car_service.services;

import com.telerikacademy.car_service.models.Maker;
import com.telerikacademy.car_service.repositories.CarMakerRepository;
import com.telerikacademy.car_service.services.contracts.CarMakerService;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CarMakerServiceImpl implements CarMakerService {

    private CarMakerRepository makerRepository;

    @Autowired
    public CarMakerServiceImpl(CarMakerRepository makerRepository) {
        this.makerRepository = makerRepository;
    }


    @Override
    public List<Maker> makerList() {

        List<Maker> makerList = makerRepository.findAll();
      if(makerList.isEmpty()){
          throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No Car Manufacturer found!");
      }else {
          return makerList;
      }
    }
}
