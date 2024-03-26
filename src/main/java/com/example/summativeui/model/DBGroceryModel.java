package com.example.summativeui.model;

import java.sql.Blob;
import java.sql.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "DBGroceryModel")

public class DBGroceryModel {
    @Id @Column private String grocery;
    @Column private String grade;
    @Column private Date sellByDate;
    @Column private int weight;
    @Column private String origin;
    @Column private boolean freezable;
    @Column private String foodCategory;
    @Column private int volume;
    @Column private int price;
    @Column private String brand;
    @Column private String allergies;
    @Column private String storage;
    @Column private String countryOrigin;
    @Column private String description;
    @Column private int alcoholicPercentage;
    @Column private int quantity;
    @Column private int nutritionalContent;
    @Column private String image;

    public DBGroceryModel(String grocery, String grade, Date sellByDate, int weight, String origin, boolean freezable, String foodCategory, int volume, int price, String brand, String allergies, String storage, String countryOrigin, String description, int alcoholicPercentage, int quantity, int nutritionalContent, String image) {
        this.grocery = grocery;
        this.grade = grade;
        this.sellByDate = sellByDate;
        this.weight = weight;
        this.origin = origin;
        this.freezable = freezable;
        this.foodCategory = foodCategory;
        this.volume = volume;
        this.price = price;
        this.brand = brand;
        this.allergies = allergies;
        this.storage = storage;
        this.countryOrigin = countryOrigin;
        this.description = description;
        this.alcoholicPercentage = alcoholicPercentage;
        this.quantity = quantity;
        this.nutritionalContent = nutritionalContent;
        this.image = image;
    }


    public int getNutritionalContent() {
        return nutritionalContent;
    }

    public int getAlcoholicPercentage() {
        return alcoholicPercentage;
    }

    public String getDescription() {
        return description;
    }

    public String getCountryOrigin() {
        return countryOrigin;
    }

    public String getStorage() {
        return storage;
    }

    public String getAllergies() {
        return allergies;
    }

    public String getBrand() {
        return brand;
    }

    public int getPrice() {
        return price;
    }

    public int getVolume() {
        return volume;
    }

    public String getFoodCategory() {
        return foodCategory;
    }

    public boolean isFreezable() {
        return freezable;
    }

    public String getOrigin() {
        return origin;
    }

    public int getWeight() {
        return weight;
    }

    public Date getSellByDate() {
        return sellByDate;
    }

    public String getGrade() {
        return grade;
    }

    public String getGrocery() {
        return grocery;
    }

    public int getQuantity() { return quantity; }

    public String getImage() {
        return image;
    }
}