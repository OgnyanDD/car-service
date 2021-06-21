package com.telerikacademy.car_service.services.contracts;

import com.telerikacademy.car_service.models.AutoServices;

import java.util.List;

public interface ServiceService {

    List<AutoServices> showAllService();

    AutoServices getServiceById(Integer id);

    void addNewService(AutoServices autoServices);

    void editService(AutoServices autoServices);

}
