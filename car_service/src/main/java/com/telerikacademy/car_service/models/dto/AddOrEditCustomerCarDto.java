package com.telerikacademy.car_service.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AddOrEditCustomerCarDto {

    @NotNull
    private Integer customerId;

    @NotNull
    private Integer carId;

    @NotNull
    @Size(min = 8,max = 20)
    @Pattern(regexp = "[A-Z0-9]{9}[A-Z0-9-]{2}[0-9]{6}",
            message = "Car registration number must contains large letters and numbers!")
    private String registration;

    @NotNull
    @Size(min = 17,max = 17)
    @Pattern(regexp = "[A-Z0-9]{9}[A-Z0-9-]{2}[0-9]{6}",
            message = "Car vin number must contains seventeen characters of large letters and numbers!")
    private String carVinNumber;

    @NotNull
    private Integer year;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getCarVinNumber() {
        return carVinNumber;
    }

    public void setCarVinNumber(String carVinNumber) {
        this.carVinNumber = carVinNumber;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
