package com.example.summativeui.model;

public class DBLoginModel {
    private String password;
    private boolean isAdmin;

    public DBLoginModel(String password, boolean isAdmin) {
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public String getPassword() {
        return password;
    }

    public boolean getAdmin() {
        return isAdmin;
    }
}
