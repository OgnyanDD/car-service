package com.telerikacademy.car_service.repositories;

import com.telerikacademy.car_service.models.Year;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManufactureYearRepository extends JpaRepository<Year,Integer> {

    List<Year> getYearsByModelId(Integer modelId);

    Year getYearById(Integer yearId);
}
