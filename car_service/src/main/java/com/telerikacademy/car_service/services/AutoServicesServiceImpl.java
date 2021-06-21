package com.telerikacademy.car_service.services;

import com.telerikacademy.car_service.exceptions.DatabaseItemNotFoundException;
import com.telerikacademy.car_service.models.AutoServices;
import com.telerikacademy.car_service.models.dto.autoservice.AddAutoServiceDto;
import com.telerikacademy.car_service.models.dto.autoservice.AutoServiceDto;
import com.telerikacademy.car_service.repositories.AutoServicesRepository;
import com.telerikacademy.car_service.services.contracts.AutoServicesService;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutoServicesServiceImpl implements AutoServicesService {

    private AutoServicesRepository servicesRepository;

    @Autowired
    public AutoServicesServiceImpl(AutoServicesRepository autoServicesRepository) {
        this.servicesRepository = autoServicesRepository;
    }

    @Override
    public List<AutoServiceDto> showAllAutoServices() {

        List<AutoServices> servicesList = servicesRepository.getAllServices();

        if (servicesList.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to access database."
            );
        } else {
            List<AutoServiceDto> autoServiceDtoList = new ArrayList<>();

            for (AutoServices a : servicesList) {
                AutoServiceDto serviceDto = mapAutoServiceToDto(a);
                autoServiceDtoList.add(serviceDto);
            }
            return autoServiceDtoList;
        }

    }

    @Override
    public List<AutoServiceDto> showAutoServiceByCategory(Integer id) {

        List<AutoServices> autoServices;

        autoServices = servicesRepository.getAllServices()
                .stream()
                .filter(service -> service.getServiceCategory().getId().equals(id))
                .filter(service -> !service.getDeleted())
                .collect(Collectors.toList());
        if (autoServices.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    String.format("Category with id %d not exist yet!", id));
        } else {
            List<AutoServiceDto> autoServiceDtoList = new ArrayList<>();

            for (AutoServices a : autoServices) {
                AutoServiceDto serviceDto = mapAutoServiceToDto(a);
                autoServiceDtoList.add(serviceDto);
            }
            return autoServiceDtoList;
        }

    }

    @Override
    public AutoServiceDto getAutoServiceById(Integer id) {

        AutoServices service = servicesRepository.getById(id);
        if (service == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    String.format("No service whit id %d found!", id));
        } else {

            return mapAutoServiceToDto(service);
        }

    }

    @Override
    public void addNewAutoService(AddAutoServiceDto autoServiceDto) {

        String service = autoServiceDto.getService();

        List<AutoServices> existServices = servicesRepository.findAll()
                .stream().filter(autoservice -> autoservice.getService().equals(service))
                .collect(Collectors.toList());

        if (existServices.isEmpty()) {
            AutoServices newService= new AutoServices();
            newService.setServiceCategory(autoServiceDto.getCategory());
            newService.setService(autoServiceDto.getService());
            newService.setPrice(autoServiceDto.getPrice());
            newService.setDeleted(false);
            newService.setCarVisits(new HashSet<>());
            servicesRepository.save(newService);
        } else {

            throw new ResponseStatusException
                    (HttpStatus.BAD_REQUEST, String.format("Service %s already exist!", service));
        }
    }

    @Override
    public void editAutoService(AddAutoServiceDto autoServiceDto) {

            AutoServices serviceToEdit = findAutoServiceById(autoServiceDto.getId());

            serviceToEdit.setServiceCategory(autoServiceDto.getCategory());
            serviceToEdit.setService(autoServiceDto.getService());
            serviceToEdit.setPrice(autoServiceDto.getPrice());
            servicesRepository.save(serviceToEdit);
    }

    @Override
    public void deleteAutoService(Integer id) {

            AutoServices serviceToDelete = findAutoServiceById(id);
            serviceToDelete.setDeleted(true);
            servicesRepository.save(serviceToDelete);
    }

    @Override
    public AutoServices findAutoServiceById(Integer id) {

        AutoServices service = servicesRepository.getById(id);
        if (service == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    String.format("No service whit id %d found!", id));
        }
        return service;
    }

    private AutoServiceDto mapAutoServiceToDto(AutoServices service) {
        AutoServiceDto autoServiceDto = new AutoServiceDto();

        autoServiceDto.setId(service.getId());
        autoServiceDto.setCategory(service.getServiceCategory().getCategory());
        autoServiceDto.setService(service.getService());
        autoServiceDto.setPrice(String.format("%.2f", service.getPrice()));
        autoServiceDto.setIsDeleted(service.getDeleted());

        return autoServiceDto;
    }
}