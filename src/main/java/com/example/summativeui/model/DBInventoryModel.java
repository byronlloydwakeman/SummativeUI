package com.example.summativeui.model;

import java.sql.Date;

public class DBInventoryModel {
    private String groceryId;
    private int quantity;
    private int totalPrice;
    private Date expirationDate;
    private Date arrivalDate;
    private Date expectedDepartureDate;
    private String location;

    public DBInventoryModel(String groceryId, int quantity, int totalPrice, Date expirationDate, Date arrivalDate, Date expectedDepartureDate, String location) {
        this.groceryId = groceryId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.expirationDate = expirationDate;
        this.arrivalDate = arrivalDate;
        this.expectedDepartureDate = expectedDepartureDate;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public Date getExpectedDepartureDate() {
        return expectedDepartureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getGroceryId() {
        return groceryId;
    }

    public String toString()
    {
        return groceryId + " - " + quantity;
    }
}
