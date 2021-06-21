package com.telerikacademy.car_service.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cars_visits")
public class CarVisit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_car_id")
    private CustomerCar customerCar;

    @NotNull
    @Column(name = "total_price")
    private double price;

    @NotNull
    @Column(name="pdf_generated")
    private Boolean pdfGenerated;

    @JsonIgnore
    @ManyToMany(cascade ={ CascadeType.MERGE,CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinTable(
            name = "cars_visits_services",
            joinColumns = @JoinColumn(name = "car_visit_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<AutoServices>autoServices;

    public CarVisit() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public CustomerCar getCustomerCar() {
        return customerCar;
    }

    public void setCustomerCar(CustomerCar customerCar) {
        this.customerCar = customerCar;
    }

    public List<AutoServices> getAutoServices() {
        return  this.autoServices;
    }

    public void setAutoServices(List<AutoServices> autoServices) {
        this.autoServices = autoServices;
    }

    public Boolean getPdfGenerated() {
        return pdfGenerated;
    }

    public void setPdfGenerated(Boolean pdfGenerated) {
        this.pdfGenerated = pdfGenerated;
    }
}
