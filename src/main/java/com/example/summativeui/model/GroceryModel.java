package com.example.summativeui.model;

import java.util.ArrayList;

public class GroceryModel {
    private boolean successful;
    private String response;
    private ArrayList<DBGroceryModel> groceryModels;
    private DBGroceryModel groceryModel;

    public GroceryModel(boolean successful, String response, DBGroceryModel groceryModel) {
        this.successful = successful;
        this.response = response;
        this.groceryModel = groceryModel;
    }

    public GroceryModel(boolean successful, String response, ArrayList<DBGroceryModel> groceryModels) {
        this.successful = successful;
        this.response = response;
        this.groceryModels = groceryModels;
    }

    public ArrayList<DBGroceryModel> getGroceryModels() {
        return groceryModels;
    }

    public String getResponse() {
        return response;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public DBGroceryModel getGroceryModel() {
        return groceryModel;
    }
}
