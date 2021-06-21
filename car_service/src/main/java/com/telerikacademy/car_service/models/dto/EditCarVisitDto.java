package com.telerikacademy.car_service.models.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class EditCarVisitDto {

    @NotNull
    private Integer carVisitId;

    private LocalDateTime date;

    @NotNull
    private List<Integer> indices;

    private Boolean isPdfGenerated;

    public Integer getCarVisitId() {
        return this.carVisitId;
    }

    public void setCarVisitId(Integer carVisitId) {
        this.carVisitId = carVisitId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<Integer> getIndices() {
        return indices;
    }

    public void setIndices(List<Integer> indices) {
        this.indices = indices;
    }

    public Boolean getPdfGenerated() {
        return isPdfGenerated;
    }

    public void setPdfGenerated(Boolean pdfGenerated) {
        isPdfGenerated = pdfGenerated;
    }
}
