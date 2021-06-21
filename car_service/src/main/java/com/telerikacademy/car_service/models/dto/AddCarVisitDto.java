package com.telerikacademy.car_service.models.dto;


import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class AddCarVisitDto {


    @NotNull
    private Integer carId;

    private LocalDateTime date;

    @NotNull
    private List<Integer> indices;

    private Boolean isPdfGenerated;

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Boolean getPdfGenerated() {
        return isPdfGenerated;
    }

    public void setPdfGenerated(Boolean pdfGenerated) {
        isPdfGenerated = pdfGenerated;
    }

    public List<Integer> getIndices() {
        return indices;
    }

    public void setIndices(List<Integer> indices) {
        this.indices = indices;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
