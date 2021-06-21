package com.telerikacademy.car_service.models.dto.customer;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CustomerCreateDto {

    private MultipartFile picture;

    @NotNull
    @Pattern(regexp = "[A-Za-z ]*",
            message = "The customer name should contain only letters!")
    @Size(
            min = 5,
            max = 60,
            message = "The customer name length must be min {min} characters and max {max} characters!")
    private String name;

    @NotNull
    @Pattern(regexp = "[0-9+]*",
            message = "The customer phone number should't contain any letters!")
    @Size(
            min = 5,
            max = 30,
            message = "The customer phone number length must be min {min} characters and max {max} characters!")
    private String phoneNumber;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(?:[a-zA-Z]{2,6})$",
            message = "Please provide a valid email address")
    @Size(
            min = 5,
            max = 255,
            message = "The customer email address length must be min {min} characters and max {max} characters!")
    private String username;

    public MultipartFile getPicture() {
        return picture;
    }

    public void setPicture(MultipartFile picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
