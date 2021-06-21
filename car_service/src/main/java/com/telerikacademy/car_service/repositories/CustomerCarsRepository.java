package com.telerikacademy.car_service.repositories;

import com.telerikacademy.car_service.models.CustomerCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerCarsRepository extends JpaRepository<CustomerCar, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM customers_cars WHERE is_deleted = false")
    List<CustomerCar> getAllCustomerCars();

    CustomerCar findByRegistration(String registration);
}
