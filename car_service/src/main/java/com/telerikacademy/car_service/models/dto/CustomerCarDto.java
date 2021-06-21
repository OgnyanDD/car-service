package com.telerikacademy.car_service.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CustomerCarDto {
    @NotNull
    private Integer carId;

    @NotNull
    private Integer customerId;

    @NotNull
    private String maker;

    @NotNull
    private String model;

    @NotNull
    private Integer year;

    @NotNull
    @Size(min = 6,max = 8)
    private String registration;

    @NotNull
    @Size(min = 17,max = 17)
    private String carVin;

    private Boolean isDeleted;


    public Integer getCarId() {
        return this.carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getMaker() {
        return this.maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegistration() {
        return this.registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getCarVin() {
        return this.carVin;
    }

    public void setCarVin(String carVin) {
        this.carVin = carVin;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
