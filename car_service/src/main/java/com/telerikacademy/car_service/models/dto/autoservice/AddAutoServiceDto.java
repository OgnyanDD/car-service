package com.telerikacademy.car_service.models.dto.autoservice;

import com.telerikacademy.car_service.models.ServiceCategory;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddAutoServiceDto {

    private Integer id;

    @NotNull
    private ServiceCategory category;

    @NotNull
    @Size(min = 10,max = 100)
    private String service;

    @NotNull
    @Min(value = 0)
    private double price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ServiceCategory getCategory() {
        return category;
    }

    public void setCategory(ServiceCategory category) {
        this.category = category;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
