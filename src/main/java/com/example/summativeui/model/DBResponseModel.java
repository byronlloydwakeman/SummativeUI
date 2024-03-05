package com.example.summativeui.model;

public class DBResponseModel {
    private boolean successful;
    private String response;

    public DBResponseModel(boolean successful, String response)
    {
        this.successful = successful;
        this.response = response;
    }

    public boolean getSuccessful()
    {
        return successful;
    }

    public String getResponse()
    {
        return response;
    }

}
