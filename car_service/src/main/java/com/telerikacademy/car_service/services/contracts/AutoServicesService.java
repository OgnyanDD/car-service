package com.telerikacademy.car_service.services.contracts;

import com.telerikacademy.car_service.models.AutoServices;
import com.telerikacademy.car_service.models.dto.autoservice.AddAutoServiceDto;
import com.telerikacademy.car_service.models.dto.autoservice.AutoServiceDto;

import java.util.List;

public interface AutoServicesService {

    List<AutoServiceDto> showAllAutoServices();

    List<AutoServiceDto>showAutoServiceByCategory(Integer id);

    AutoServiceDto getAutoServiceById(Integer id);

    AutoServices findAutoServiceById(Integer id);

    void addNewAutoService(AddAutoServiceDto autoServiceDto);

    void editAutoService(AddAutoServiceDto autoServiceDto);

    void deleteAutoService(Integer autoserviceId);





}
