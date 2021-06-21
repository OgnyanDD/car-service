package com.telerikacademy.car_service.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "customers_cars")
public class CustomerCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "year_id")
    private Year year;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @NotNull
    @Column(name = "car_vin")
    @Size(min = 17,max = 17)
    @Pattern(regexp = "[A-Z0-9]*",
            message = "Car vin number must contains seventeen characters of large letters and numbers!")
    private String car_vin;

    @NotNull
    @Column(name="car_registration_plate")
    @Size(min = 6,max = 10)
    @Pattern(regexp = "[A-Z0-9\u0400-\u04FF]*",
            message = "Car registration number must contains large letters and numbers!")
    private String registration;

    @NotNull
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @JsonIgnore
    @OneToMany(mappedBy = "customerCar",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<CarVisit> carVisits;

    public CustomerCar() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getCar_vin() {
        return car_vin;
    }

    public void setCar_vin(String car_vin) {
        this.car_vin = car_vin;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public List<CarVisit> getCarVisits() {
        return this.carVisits;
    }

    public void setCarVisits(List<CarVisit> carVisits) {
        this.carVisits = carVisits;
    }
}
