package com.telerikacademy.car_service.repositories;

import com.telerikacademy.car_service.models.AutoServices;
import com.telerikacademy.car_service.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoServicesRepository extends JpaRepository<AutoServices, Integer> {

    @Query(nativeQuery = true,value = "SELECT * FROM services as s where s.id=?1")
    AutoServices getById(Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM services where is_deleted=false")
    List<AutoServices> getAllServices();
}
