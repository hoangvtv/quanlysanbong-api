package com.example.doannmcnpm.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@Table
public class SoccerField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @NotBlank
    private String name;

    @Column
    private String description;

    @Column
    @Min(value = 0)
    @Max(value = 1)
    private int status;

    @Column
    private double price;

    @ManyToOne
//    @JsonIgnore
    @JoinColumn(name = "category_id")
    Category category;

    @ManyToOne
//    @JsonIgnore
    @JoinColumn(name = "yardLocation_id")
    YardLocation yardLocation;

    public SoccerField(int id, String name, String description, int status, double price, Category category, YardLocation yardLocation) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.price=price;
        this.category = category;
        this.yardLocation=yardLocation;
    }

    public SoccerField() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public YardLocation getYardLocation() {
        return yardLocation;
    }

    public void setYardLocation(YardLocation yardLocation) {
        this.yardLocation = yardLocation;
    }
}
