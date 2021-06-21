package com.telerikacademy.car_service.models.dto;

import com.telerikacademy.car_service.models.AutoServices;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CarVisitDto {

    private int id;

    private LocalDateTime date;

    private String customer;

    private String CustomerCar;

    private String CarPlateNumber;

    private List<AutoServices> services;

    private double price;

    private Boolean pdfGenerated;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return this.date.format(DateTimeFormatter.ISO_LOCAL_DATE);

    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCustomerCar() {
        return CustomerCar;
    }

    public void setCustomerCar(String customerCar) {
        CustomerCar = customerCar;
    }

    public String getCarPlateNumber() {
        return CarPlateNumber;
    }

    public void setCarPlateNumber(String carPlateNumber) {
        CarPlateNumber = carPlateNumber;
    }

    public List<AutoServices> getServices() {
        return this.services;
    }

    public void setServices(List<AutoServices> services) {
        this.services = services;
    }

    public String getPrice() {
        return String.format("%.2f",price);
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Boolean getPdfGenerated() {
        return pdfGenerated;
    }

    public void setPdfGenerated(Boolean pdfGenerated) {
        this.pdfGenerated = pdfGenerated;
    }
}
