package com.telerikacademy.car_service.repositories;

import com.telerikacademy.car_service.models.Maker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarMakerRepository extends JpaRepository<Maker,Integer> {
}
