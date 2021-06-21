package com.telerikacademy.car_service.repositories;

import com.telerikacademy.car_service.models.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarModelRepository extends JpaRepository<Model,Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM cars_models WHERE make_id=?1")
    List<Model> getModelsByMakerId(Integer makerId);
}
