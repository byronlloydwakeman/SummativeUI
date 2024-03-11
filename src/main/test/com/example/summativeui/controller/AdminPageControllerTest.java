package com.example.summativeui.controller;

import com.example.summativeui.Env;
import com.example.summativeui.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class AdminPageControllerTest {
    private ResourceBundle lang = ResourceBundle.getBundle("lang/en", Locale.ENGLISH);

    @Start
    public void start(Stage stage) throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("admin-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 400);
        stage.setTitle("Test Grocery Stock Management");
        stage.setScene(scene);
        stage.show();
    }

    @AfterEach
    public void RemoveUserEntries()
    {
        try
        {
            Connection conn = TestDatabase.InitDBConnection();
            TestDatabase.ClearDBTable(conn, Env.readEnv().get("testdbtableusers"));
            conn.close();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void createAccount(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("testUsername");
        robot.clickOn("#password");
        robot.write("testPassword");
        robot.clickOn("#createAccount");

        final javafx.stage.Stage actualAlertDialog = getTopModalStage(robot);
        assertNotNull(actualAlertDialog);

        final DialogPane dialogPane = (DialogPane) actualAlertDialog.getScene().getRoot();
        assertEquals(dialogPane.getId(), lang.getString("dialog.info.id"));
        // Make sure to close notification prompt for the next text
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
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