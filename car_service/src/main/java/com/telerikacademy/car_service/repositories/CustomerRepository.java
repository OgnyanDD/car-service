package com.telerikacademy.car_service.repositories;

import com.telerikacademy.car_service.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM customers WHERE is_deleted = false")
    List<Customer> getAllCustomers();

    Customer findByEmail(String email);

    Customer getById(Integer customerId);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE customers AS c SET c.is_deleted=true WHERE c.id=?1")
    void deleteCustomerById(Integer customerId);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE users AS u SET u.enabled=false WHERE u.username=?1")
    void disableUserAccount(String username);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE customers_cars AS cc SET cc.is_deleted = true WHERE cc.customer_id=?1")
    void deleteCustomerCars(Integer customerId);

    Boolean existsCustomerByEmail(String customerEmail);

    @Query(nativeQuery = true, value = "SELECT u.enabled FROM users AS u WHERE u.username=?1")
    Byte checkCustomerAccountIsActive(String customerEmail);
}
