package com.example.summativeui;

import com.example.summativeui.database.DatabaseEndpoint;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class HelloApplication extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("inventory-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Grocery Stock Management");
        stage.setScene(scene);
        stage.show();

        //Initialize DB connection
        try {
            new DatabaseEndpoint();
        } catch (ClassNotFoundException e) {
            Notifications.ShowError("Error", "ClassNotFound Error", e.getMessage());
        } catch (SQLException e) {
            Notifications.ShowError("Error", "SQL Error", e.getMessage());
        }
    }

    public static void changeScene(String fxml, double x, double y) throws IOException {
        Parent pane = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource(fxml)));
        primaryStage.getScene().setRoot(pane);
        primaryStage.setHeight(y);
        primaryStage.setWidth(x);
    }

    public static void main(String[] args) {
        launch();
    }
}