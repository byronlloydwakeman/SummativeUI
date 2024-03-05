package com.example.summativeui.controller;

import com.example.summativeui.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;


import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(ApplicationExtension.class)
class LoginPageControllerTest {
    private ResourceBundle lang = ResourceBundle.getBundle("lang/en", Locale.ENGLISH);

    @Start
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Test Grocery Stock Management");
        stage.setScene(scene);
        stage.show();
    }

    void LoginTemp(FxRobot robot, String username, String password, String expectedDialog)
    {
        robot.clickOn("#username");
        robot.write("admin");
        robot.clickOn("#password");
        robot.write("password1");
        robot.clickOn("#login");

        final javafx.stage.Stage actualAlertDialog = getTopModalStage(robot);
        assertNotNull(actualAlertDialog);

        final DialogPane dialogPane = (DialogPane) actualAlertDialog.getScene().getRoot();
        assertEquals(dialogPane.getId(), lang.getString("dialog.warning.id"));
    }


    @Test
    void LoginSuccessful(FxRobot robot)
    {

    }

    @Test
    void login(FxRobot robot) {
        //Write username
        robot.clickOn("#username");
        robot.write("admin");
        robot.clickOn("#password");
        robot.write("password1");
        robot.clickOn("#login");

        final javafx.stage.Stage actualAlertDialog = getTopModalStage(robot);
        assertNotNull(actualAlertDialog);

        final DialogPane dialogPane = (DialogPane) actualAlertDialog.getScene().getRoot();
        assertEquals(dialogPane.getId(), lang.getString("dialog.warning.id"));
    }

    private Stage getTopModalStage(FxRobot robot) {
        final List<Window> allWindows = new ArrayList<>(robot.robotContext().getWindowFinder().listWindows());
        Collections.reverse(allWindows);

        return (javafx.stage.Stage) allWindows
                .stream()
                .filter(window -> window instanceof javafx.stage.Stage)
                .filter(window -> ((javafx.stage.Stage) window).getModality() == Modality.APPLICATION_MODAL)
                .findFirst()
                .orElse(null);
    }
}

