package controller;

import com.example.summativeui.Notifications;
import com.example.summativeui.database.DatabaseEndpoint;
import com.example.summativeui.model.DBGroceryModel;
import com.example.summativeui.model.DBInventoryModel;
import com.example.summativeui.model.GroceryModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.scene.text.TextAlignment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryPageController {
    @FXML
    private ListView inventoryList = new ListView();
    @FXML
    private VBox groceryViewer;
    @FXML
    private Label introLabel;
    @FXML
    private VBox leftSide;
    @FXML
    private VBox rightSide;

    public void fetchInventoryItems()
    {
        try {
            DatabaseEndpoint endpoint = new DatabaseEndpoint();
            inventoryList.setItems(FXCollections.observableArrayList(endpoint.ReadAllInventory().getInventoryModels()));
        } catch (ClassNotFoundException e) {
            Notifications.ShowError("Error", "Class not found", e.getMessage());
        } catch (SQLException e) {
            Notifications.ShowError("Error", "SQL Error", e.getMessage());
        } catch (Exception e) {
            Notifications.ShowError("", "Error", e.getMessage());
        }
    }

    private void renderLabel(VBox parentContainer, String content, String textFormatting)
    {
        if (content != null && !content.equals("0"))
        {
            parentContainer.getChildren().add(new Label(String.format(textFormatting, content)));
        }
    }

    private void renderLabel(VBox parentContainer, String content)
    {
        if (content != null && !content.equals("0"))
        {
            Label temp = new Label(content);
            parentContainer.getChildren().add(temp);
        }
    }

    private void renderSubtitleLabel(VBox parentContainer, String content)
    {
        if (content != null && !content.equals("0"))
        {
            Label temp = new Label(content);
            temp.getStyleClass().add("grocery-subtitle");
            parentContainer.getChildren().add(temp);
        }
    }

    @FXML
    protected void handleInventoryItemClick()
    {
        try {
            DBInventoryModel dbInventoryModel = (DBInventoryModel) inventoryList.getSelectionModel().getSelectedItem();
            GroceryModel groceryModel = DatabaseEndpoint.ReadGroceryById((dbInventoryModel.getGroceryId()));

            Image image = new Image(new FileInputStream("src/main/resources/com/example/summativeui/images/" + groceryModel.getGroceryModel().getImage()));
            ImageView imageView = new ImageView(image);

            DBGroceryModel dbGroceryModel = groceryModel.getGroceryModel();

            // Clear children
            leftSide.getChildren().clear();
            rightSide.getChildren().clear();

            //Set the grocery name
            introLabel.setText(dbGroceryModel.getGrocery());
            introLabel.getStyleClass().add("grocery-title");

            // Right side
            rightSide.setPadding(new Insets(10.0));
            rightSide.getChildren().add(imageView);

            renderSubtitleLabel(rightSide,"Inventory Level");
            HBox inventoryRow = new HBox();
            rightSide.getChildren().add(inventoryRow);
            inventoryRow.setAlignment(Pos.TOP_CENTER);

            Button minus = new Button();
            minus.setText("➖");
            minus.getStyleClass().add("inventory-button");
            Label inventoryQuantity = new Label();

            inventoryQuantity.setText(String.valueOf(dbInventoryModel.getQuantity()));
            inventoryQuantity.getStyleClass().add("inventory-quantity");

            Button plus = new Button();
            plus.setText("➕");
            plus.getStyleClass().add("inventory-button");
            inventoryRow.getChildren().addAll(minus, inventoryQuantity, plus);

            renderSubtitleLabel(rightSide, "Nutritional Content");


            // Left side
            leftSide.setPadding(new Insets(20.0));
            renderSubtitleLabel(leftSide, String.format("£%s", dbGroceryModel.getPrice() / 100));

            // Description
            renderSubtitleLabel(leftSide, "Description");
            renderLabel(leftSide, String.valueOf(dbGroceryModel.getWeight()), "Weight - %s");
            renderLabel(leftSide, String.valueOf(dbGroceryModel.getSellByDate()), "Sell By Date - %s");
            renderLabel(leftSide, dbGroceryModel.getCountryOrigin(), "Country Origin - %s");
            renderLabel(leftSide, dbGroceryModel.getOrigin(), "Origin - %s");
            renderLabel(leftSide, dbGroceryModel.isFreezable() ? "Freezable" : null);

            renderSubtitleLabel(leftSide, "Storage");
            renderLabel(leftSide, dbGroceryModel.getStorage());

            renderSubtitleLabel(leftSide, "Category");
            renderLabel(leftSide, dbGroceryModel.getFoodCategory());

            renderSubtitleLabel(leftSide, "Grade");
            renderLabel(leftSide, dbGroceryModel.getGrade());

        } catch (SQLException e) {
            Notifications.ShowError("Error", "SQL Error", e.getMessage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
