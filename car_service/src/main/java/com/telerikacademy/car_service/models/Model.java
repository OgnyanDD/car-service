package com.telerikacademy.car_service.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cars_models")
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;


    @NotNull
    @Column(name = "model")
    private String model;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "make_id")
    private Maker maker;

    public Model() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Maker getMaker() {
        return maker;
    }

    public void setMaker(Maker maker) {
        this.maker = maker;
    }
}
