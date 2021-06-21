package com.telerikacademy.car_service.services.contracts;

import com.telerikacademy.car_service.models.CustomerCar;
import com.telerikacademy.car_service.models.dto.*;

import java.util.List;

public interface CustomersCarsService {

    List<CustomerCarInfoDto> showAllCars();

    List<CustomerCarDto>showAllCustomerCars(Integer customerId);

    CustomerCarDto findByRegistration(String registration);

    CustomerCar findCarById(Integer carId);

    CustomerCarDto customerCarDto(Integer carId);

    void editCustomerCar(AddOrEditCustomerCarDto editCustomerCarDto);

    void deleteCustomerCar(Integer carId);

    void addNewCustomerCar(AddOrEditCustomerCarDto addCustomerCarDto);

}
