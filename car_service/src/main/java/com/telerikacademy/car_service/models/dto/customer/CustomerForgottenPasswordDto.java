package com.telerikacademy.car_service.models.dto.customer;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CustomerForgottenPasswordDto {

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(?:[a-zA-Z]{2,6})$",
            message = "Please provide a valid email address")
    @Size(
            min = 5,
            max = 255,
            message = "The customer email address length must be min {min} characters and max {max} characters!")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
