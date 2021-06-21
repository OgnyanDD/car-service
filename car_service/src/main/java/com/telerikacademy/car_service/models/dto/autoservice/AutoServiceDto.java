package com.telerikacademy.car_service.models.dto.autoservice;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AutoServiceDto {

    @NotNull
    private Integer id;

    @NotNull
    private String category;

    @NotNull
    @Size(max = 100)
    private String service;

    @NotNull
    private String price;

    private Boolean isDeleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
