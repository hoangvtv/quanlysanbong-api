package com.example.doannmcnpm.model;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @NotBlank
    private String categoryName;

    @Column
    private String description;

    public Category( String categoryName, String description) {
        this.categoryName = categoryName;
        this.description = description;
    }

    public Category() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
