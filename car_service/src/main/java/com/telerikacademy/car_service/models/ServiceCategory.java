package com.telerikacademy.car_service.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "service_categories")
public class ServiceCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Column(name = "category")
    private String category;

    @JsonIgnore
    @OneToMany(mappedBy = "serviceCategory",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<AutoServices> autoServices;


    public ServiceCategory() {

    }

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

    public List<AutoServices> getAutoServices() {
        return autoServices;
    }

    public void setAutoServices(List<AutoServices> autoServices) {
        this.autoServices = autoServices;
    }

}
