package controller;

import com.example.summativeui.HelloApplication;
import com.example.summativeui.Notifications;
import com.example.summativeui.database.DatabaseEndpoint;
import com.example.summativeui.model.DBResponseModel;
import com.example.summativeui.model.LoginModel;
import com.example.summativeui.model.UserModel;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class AdminPageController {
    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private CheckBox isAdmin;

    @FXML
    protected void CreateAccount() {
        try
        {
            DatabaseEndpoint endpoint = new DatabaseEndpoint();
            if(!UserModel.getInstance().getIsAdmin())
            {
                Notifications.ShowError("", "Invalid admin account", "Please enter a valid admin account");
            }

            DBResponseModel response = endpoint.CreateUser(username.getText(), password.getText(), isAdmin.isSelected());
            if(response.getSuccessful())
            {
                Notifications.ShowInfo("", "Success", response.getResponse());
            }
            else
            {
                Notifications.ShowError("", "Error", response.getResponse());
            }
        }
        catch(Exception e)
        {
            Notifications.ShowError("", "Error", e.getMessage());
        }
    }
}
