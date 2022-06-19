package com.example.doannmcnpm.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class SoccerFieldDto {

    private int id;
    private @NotNull String name;
    private @NotNull String description;
    private @NotNull double price;
    private @NotNull int status;
    private @NotNull int categoryId;
    private @NotNull int yardLocationId;

    public SoccerFieldDto(int id, String name, String description, double price,  int status, int categoryId, int yardLocationId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price= price;
        this.status=status;
        this.categoryId = categoryId;
        this.yardLocationId=yardLocationId;
    }

    public SoccerFieldDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getYardLocationId() {
        return yardLocationId;
    }

    public void setYardLocationId(int yardLocationId) {
        this.yardLocationId = yardLocationId;
    }
}
