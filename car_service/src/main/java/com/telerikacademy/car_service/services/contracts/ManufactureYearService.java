package com.telerikacademy.car_service.services.contracts;

import com.telerikacademy.car_service.models.Year;
import com.telerikacademy.car_service.models.dto.ManufactureYearDto;

import java.util.List;

public interface ManufactureYearService {

    List<ManufactureYearDto> getModelYearsByModelId(Integer modelId);

    Year getYearById(Integer yearId);
}
