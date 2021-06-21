package com.telerikacademy.car_service.models.dto;




public class CarVisitsInfoDto {

    private Integer id;

    private String date;

    private String carRegistration;

    private Double price;

    private Boolean pdfGenerated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCarRegistration() {
        return carRegistration;
    }

    public void setCarRegistration(String carRegistration) {
        this.carRegistration = carRegistration;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getPdfGenerated() {
        return pdfGenerated;
    }

    public void setPdfGenerated(Boolean pdfGenerated) {
        this.pdfGenerated = pdfGenerated;
    }
}
