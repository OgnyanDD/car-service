package com.telerikacademy.car_service.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;


@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "picture")
    private String picture;

    @NotNull
    @Column(name = "name")
    @Pattern(regexp = "[A-Za-z ]*",
            message = "The customer name should contain only letters!")
    @Size(
            min = 5,
            max = 60,
            message = "The customer name length must be min {min} characters and max {max} characters!")
    private String name;

    @NotNull
    @Column(name = "email")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(?:[a-zA-Z]{2,6})$",
            message = "Please provide a valid email address")
    @Size(
            min = 3,
            max = 255,
            message = "The customer email address length must be min {min} characters and max {max} characters!")
    private String email;

    @NotNull
    @Column(name = "phone_number")
    @Pattern(regexp = "[0-9+]*",
            message = "The customer phone number should't contain any letters!")
    @Size(
            min = 5,
            max = 30,
            message = "The customer phone number length must be min {min} characters and max {max} characters!")
    private String phoneNumber;

    @NotNull
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CustomerCar> customerCars;

    public Customer() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public List<CustomerCar> getCustomerCars() {
        return customerCars;
    }

    public void setCustomerCars(List<CustomerCar> customerCars) {
        this.customerCars = customerCars;
    }
}