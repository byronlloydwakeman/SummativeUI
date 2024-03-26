package com.example.summativeui.model;

import java.util.ArrayList;

public class InventoryModel {
    private ArrayList<DBInventoryModel> inventoryModels;
    private String response;
    private boolean successful;

    public InventoryModel(boolean successful, String response, ArrayList<DBInventoryModel> inventoryModels) {
        this.inventoryModels = inventoryModels;
        this.response = response;
        this.successful = successful;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getResponse() {
        return response;
    }

    public ArrayList<DBInventoryModel> getInventoryModels() {
        return inventoryModels;
    }
}
