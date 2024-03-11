package controller;

import com.example.summativeui.HelloApplication;
import com.example.summativeui.Notifications;
import com.example.summativeui.model.LoginModel;
import com.example.summativeui.database.DatabaseEndpoint;
import com.example.summativeui.model.UserModel;
import javafx.scene.control.TextField;

import javafx.fxml.FXML;


public class LoginPageController {
    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    protected void Login() {
        // Call the function to actually login
        try {
            DatabaseEndpoint endpoint = new DatabaseEndpoint();
            String usernameStr = username.getText();
            String passwordStr = password.getText();
            LoginModel response = endpoint.Login(usernameStr, passwordStr);
            if(response.getSuccessful())
            {
                //Set instance to be re-used
                UserModel.setInstance(usernameStr, passwordStr, response.getIsAdmin());
                Notifications.ShowInfo("", "Login Successful", "");
                HelloApplication.changeScene("admin-page.fxml", 320, 400);
            }
            else
            {
                Notifications.ShowWarning("", "Login Error", response.getResponse());
            }
        }
        catch(Exception e){
            Notifications.ShowError("", "Login Error", e.getMessage());
        }
    }
}
