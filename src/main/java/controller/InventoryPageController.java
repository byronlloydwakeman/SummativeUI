package controller;

import com.example.summativeui.Notifications;
import com.example.summativeui.database.DatabaseEndpoint;
import com.example.summativeui.database.HibernateDBEndpoint;
import com.example.summativeui.model.DBGroceryModel;
import com.example.summativeui.model.DBInventoryModel;
import com.example.summativeui.model.GroceryModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryPageController {
    @FXML
    private ListView inventoryList = new ListView();

    @FXML
    private VBox groceryViewer;

    public void fetchInventoryItems()
    {
        List<?> groceries = HibernateDBEndpoint.getGrocery("Coca-Cola Classic 500ml");
        System.out.println(groceries);
//        try {
//            DatabaseEndpoint endpoint = new DatabaseEndpoint();
////            inventoryList.setItems(FXCollections.observableArrayList(endpoint.ReadAllGroceries().getGroceryModels()));
//            inventoryList.setItems(FXCollections.observableArrayList(endpoint.ReadAllInventory().getInventoryModels()));
//        } catch (ClassNotFoundException e) {
//            Notifications.ShowError("Error", "Class not found", e.getMessage());
//        } catch (SQLException e) {
//            Notifications.ShowError("Error", "SQL Error", e.getMessage());
//        } catch (Exception e) {
//            Notifications.ShowError("", "Error", e.getMessage());
//        }
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
            parentContainer.getChildren().add(new Label(content));
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

            groceryViewer.getChildren().clear();

            //Add the grocery name
            renderLabel(groceryViewer, dbGroceryModel.getGrocery());

            //Add HBox with VBoxes
            HBox hBox = new HBox();
            groceryViewer.getChildren().add(hBox);

            //Add VBoxes to HBox
            VBox leftSide = new VBox();
            VBox rightSide = new VBox();

            leftSide.getStyleClass().add("hbox-hgrow-always");
            rightSide.getStyleClass().add("hbox-hgrow-always");

            groceryViewer.getChildren().add(leftSide);
            groceryViewer.getChildren().add(rightSide);

            // Right side
            rightSide.getChildren().add(imageView);

            // Left side
            renderLabel(leftSide, String.valueOf(dbGroceryModel.getPrice() / 100), "£%s");

            // Description
            renderLabel(leftSide, "Description");
            renderLabel(leftSide, String.valueOf(dbGroceryModel.getWeight()), "Weight - %s");
            renderLabel(leftSide, String.valueOf(dbGroceryModel.getSellByDate()), "Sell By Date - %s");
            renderLabel(leftSide, dbGroceryModel.getCountryOrigin(), "Country Origin - %s");
            renderLabel(leftSide, dbGroceryModel.getOrigin(), "Origin - %s");
            renderLabel(leftSide, dbGroceryModel.isFreezable() ? "Freezable" : null);

            renderLabel(leftSide, "Storage");
            renderLabel(leftSide, dbGroceryModel.getStorage());

            renderLabel(leftSide, "Category");
            renderLabel(leftSide, dbGroceryModel.getFoodCategory());

            renderLabel(leftSide, "Grade");
            renderLabel(leftSide, dbGroceryModel.getGrade());

        } catch (SQLException e) {
            Notifications.ShowError("Error", "SQL Error", e.getMessage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
