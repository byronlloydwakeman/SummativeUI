package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Circle;

public class InventoryPageController {
    @FXML
    private FlowPane inventoryFlowPane;

    public void generateItem()
    {
        inventoryFlowPane.getChildren().add(new Circle(10));
    }
}
