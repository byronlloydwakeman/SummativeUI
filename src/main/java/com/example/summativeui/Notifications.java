package com.example.summativeui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.util.Locale;
import java.util.ResourceBundle;

public class Notifications {
    private static ResourceBundle lang = ResourceBundle.getBundle("lang/en", Locale.ENGLISH);

    private static void ShowNotification(String title, String header, String content, Alert alert){
        // Set the title and header text of the alert
        alert.setTitle(title);
        alert.setHeaderText(header);

        // Set the content text of the alert
        alert.setContentText(content);

        // Show the alert dialog
        alert.showAndWait();
    }

    public static void ShowError(String title, String header, String content){
        Alert alert = new Alert(AlertType.ERROR);
        alert.getDialogPane().setId(lang.getString("dialog.error.id"));
        ShowNotification(title, header, content, alert);
    }

    public static void ShowInfo(String title, String header, String content){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.getDialogPane().setId(lang.getString("dialog.info.id"));
        ShowNotification(title, header, content, alert);
    }

    public static void ShowWarning(String title, String header, String content){
        Alert alert = new Alert(AlertType.WARNING);
        alert.getDialogPane().setId(lang.getString("dialog.warning.id"));
        ShowNotification(title, header, content, alert);
    }

}
