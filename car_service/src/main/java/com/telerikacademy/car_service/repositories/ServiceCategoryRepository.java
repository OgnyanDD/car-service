package com.telerikacademy.car_service.repositories;

import com.telerikacademy.car_service.models.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory,Integer> {
}
