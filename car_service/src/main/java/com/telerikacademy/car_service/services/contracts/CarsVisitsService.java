package com.telerikacademy.car_service.services.contracts;


import com.telerikacademy.car_service.models.CarVisit;
import com.telerikacademy.car_service.models.dto.*;

import java.util.List;

public interface CarsVisitsService {

    List<CarVisitsInfoDto> getAllCarsVisits();

    List<CarVisitsInfoDto> singleCarVisits(Integer carId);

    CarVisitDto getCarVisitDtoById(int id);

    void addCarVisit(AddCarVisitDto addCarVisitDto);

    void editCarVisits(EditCarVisitDto editCarVisitDto);

    void editPdfGenerated(CarVisitDto carVisitDto);

}
