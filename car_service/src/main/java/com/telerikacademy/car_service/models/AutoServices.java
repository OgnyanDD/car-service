package com.telerikacademy.car_service.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "services")
public class AutoServices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="category_id")
    private ServiceCategory serviceCategory;

    @NotNull
    @Column(name = "service")
    private String service;


    @NotNull
    @Column(name = "price")
    private double price;

    @NotNull
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @JsonIgnore
    @ManyToMany(mappedBy = "autoServices",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<CarVisit> carVisits;


    public AutoServices() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Set<CarVisit> getCarVisits() {
        return carVisits;
    }

    public void setCarVisits(Set<CarVisit> carVisits) {
        this.carVisits = carVisits;
    }

    public ServiceCategory getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(ServiceCategory serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    @Override
    public String toString() {
        return String.format("Category: " + "%s" + "\r\n" +
                "Service: " + "%s" + "\r\n" +
                "Price: " + "%.2f", getServiceCategory().getCategory(), service, price);
    }
}
