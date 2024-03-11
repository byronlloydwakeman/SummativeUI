package com.example.summativeui.controller;

import com.example.summativeui.Env;
import com.example.summativeui.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(ApplicationExtension.class)
class LoginPageControllerTest {
    private ResourceBundle lang = ResourceBundle.getBundle("lang/en", Locale.ENGLISH);
    private static String correctUsername = "admin";
    private static String correctPassword = "password";

    @Start
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Test Grocery Stock Management");
        stage.setScene(scene);
        stage.show();
    }

    @BeforeAll
    public static void CreateTestUser() throws Exception {
        // Add user to testdb
        Connection conn = TestDatabase.InitDBConnection();
        Statement stmt = conn.createStatement();
        String query = "INSERT INTO " + Env.readEnv().get("testdbtableusers") + " (username, password, admin) " +
                "VALUES ('" + correctUsername + "', '" + correctPassword + "', " + true + ")";
        int inserted = stmt.executeUpdate(query);
        if(inserted != 1)
        {
            throw new Exception("Error adding test user to db");
        }
        conn.close();
    }

    @AfterAll
    public static void RemoveTestUser() throws SQLException, ClassNotFoundException {
        Connection conn = TestDatabase.InitDBConnection();
        TestDatabase.ClearDBTable(conn, Env.readEnv().get("testdbtableusers"));
    }

    void LoginTemp(FxRobot robot, String username, String password, String expectedDialog)
    {
        robot.clickOn("#username");
        robot.write(username);
        robot.clickOn("#password");
        robot.write(password);
        robot.clickOn("#login");

        final javafx.stage.Stage actualAlertDialog = getTopModalStage(robot);
        assertNotNull(actualAlertDialog);

        final DialogPane dialogPane = (DialogPane) actualAlertDialog.getScene().getRoot();
        assertEquals(dialogPane.getId(), lang.getString(expectedDialog));
        // Make sure to close notification prompt for the next text
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
    }

    @Test
    void LoginSuccessful(FxRobot robot)
    {
        LoginTemp(robot, LoginPageControllerTest.correctUsername, LoginPageControllerTest.correctPassword, "dialog.info.id");
    }

    @Test
    void LoginIncorrectUsername(FxRobot robot)
    {
        LoginTemp(robot, "admin1", "password", "dialog.warning.id");
    }

    @Test
    void LoginIncorrectPassword(FxRobot robot)
    {
        LoginTemp(robot, "admin", "password1", "dialog.warning.id");
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

