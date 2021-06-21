package com.telerikacademy.car_service.services;

import com.telerikacademy.car_service.models.Year;
import com.telerikacademy.car_service.models.dto.ManufactureYearDto;
import com.telerikacademy.car_service.repositories.ManufactureYearRepository;
import com.telerikacademy.car_service.services.contracts.ManufactureYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManufactureYearServiceImpl implements ManufactureYearService {

    private ManufactureYearRepository yearRepository;

    @Autowired
    public ManufactureYearServiceImpl(ManufactureYearRepository yearRepository) {
        this.yearRepository = yearRepository;
    }


    @Override
    public List<ManufactureYearDto> getModelYearsByModelId(Integer modelId) {

        return yearRepository.getYearsByModelId(modelId)
                .stream()
                .map(this::mapManufactureYearDto)
                .collect(Collectors.toList());
    }

    @Override
    public Year getYearById(Integer yearId) {
        return yearRepository.getYearById(yearId);
    }

    private ManufactureYearDto mapManufactureYearDto(Year year) {
        ManufactureYearDto manufactureYearDto = new ManufactureYearDto();
        manufactureYearDto.setId(year.getId());
        manufactureYearDto.setYear(year.getYear());
        manufactureYearDto.setModel(year.getModel().getModel());

        return manufactureYearDto;
    }
}
