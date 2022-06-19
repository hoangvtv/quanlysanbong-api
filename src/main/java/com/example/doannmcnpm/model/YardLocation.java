package com.example.doannmcnpm.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table
public class YardLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @NotNull
    @Size(max = 50, message = "The field name should not exceed 50 characters")
    private String name;

    @Column
    @Size(max = 30)
    private String apartmentNumber;

    @Column
    private String streetName;

    @Column
    private String wardName;

    @Column
    @Size(max = 50)
    private String districtName;


    @Column
    private String note;

    public YardLocation(int id, String name, String apartmentNumber, String streetName, String wardName, String districtName, String note) {
        this.id = id;
        this.name = name;
        this.apartmentNumber = apartmentNumber;
        this.streetName = streetName;
        this.wardName = wardName;
        this.districtName = districtName;
        this.note = note;
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

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public YardLocation() {
    }


}
