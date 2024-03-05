package com.example.summativeui.model;

public class UserModel {
    private String username;
    private String password;
    private boolean isAdmin;

    // Value is for testing, delete in prod
    private static UserModel instance = new UserModel("admin", "password", true);

    public UserModel(String username, String password, boolean isAdmin){
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public static void setInstance(String username, String password, boolean isAdmin)
    {
        instance = new UserModel(username, password, isAdmin);
    }

    public static UserModel getInstance()
    {
        return instance;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public boolean getIsAdmin()
    {
        return isAdmin;
    }
}

