package com.example.summativeui.model;

public class LoginModel {
    private String response;
    private boolean successful;
    private boolean isAdmin;

    public LoginModel(String response, boolean successful, boolean isAdmin)
    {
        this.response = response;
        this.successful = successful;
        this.isAdmin = isAdmin;
    }

    public String getResponse()
    {
        return response;
    }

    public boolean getSuccessful()
    {
        return successful;
    }

    public boolean getIsAdmin() { return isAdmin; }

}
