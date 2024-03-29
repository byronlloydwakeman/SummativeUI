package controller;

import com.example.summativeui.HelloApplication;
import com.example.summativeui.Notifications;
import com.example.summativeui.model.LoginModel;
import com.example.summativeui.database.DatabaseEndpoint;
import com.example.summativeui.model.UserModel;
import javafx.scene.control.TextField;

import javafx.fxml.FXML;

import java.io.IOException;
import java.sql.SQLException;


public class LoginPageController {
    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    protected void Login() {
        // Call the function to actually login

        try {
            String usernameStr = username.getText();
            String passwordStr = password.getText();
            LoginModel response = DatabaseEndpoint.Login(usernameStr, passwordStr);
            if(response.getSuccessful())
            {
                //Set instance to be re-used
                UserModel.setInstance(usernameStr, passwordStr, response.getIsAdmin());
                Notifications.ShowInfo("", "Login Successful", "");
                // We don't want the page to navigate if we're testing
                if(!System.getenv("ENVIRONMENT").equals("test"))
                {
                    HelloApplication.changeScene("inventory-page.fxml", 1920, 1080);
                }
            }
            else
            {
                Notifications.ShowWarning("", "Login Error", response.getResponse());
            }
        } catch (SQLException e) {
            Notifications.ShowError("Error", "SQL Error", e.getMessage());
        } catch(IOException e){
            Notifications.ShowError("Error", "IOException", e.getMessage());
        }



    }
}
