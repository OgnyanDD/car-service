package com.telerikacademy.car_service.repositories;

import com.telerikacademy.car_service.models.CarVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarsVisitsRepository extends JpaRepository<CarVisit, Integer> {

    @Query(nativeQuery = true,value = "SELECT * FROM cars_visits WHERE id =?1")
    CarVisit findCarVisitById(Integer id);

    @Query(nativeQuery = true,value = "SELECT * FROM cars_visits as c WHERE c.customer_car_id=?1")
    List<CarVisit> findAllByCustomerCar(Integer carId);

}
