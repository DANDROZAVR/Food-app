package main.Application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class sampleController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button show_all_products;

    @FXML
    private Button show_all_recipes;
    @FXML
    void openSettings(ActionEvent event) {
    }
    @FXML
    void goToHome(ActionEvent event) {
    }
    @FXML
    private Button ButtonAddProducts;
    @FXML
    void initialize() {
        assert show_all_products != null : "fx:id=\"show_all_products\" was not injected: check your FXML file 'sample.fxml'.";
        show_all_products.setOnAction(event -> {
            FXMLLoader loader = LoadXML.load("ForProducts.fxml");
            ForProductsController ctr = loader.getController();
            Parent root = loader.getRoot();
            ((Stage) show_all_products.getScene().getWindow()).setScene(new Scene(root));
        });
        assert show_all_recipes != null : "fx:id=\"show_all_recipes\" was not injected: check your FXML file 'sample.fxml'.";
        show_all_recipes.setOnAction(event -> {
            FXMLLoader loader = LoadXML.load("ForRecipes.fxml");
            Parent root = loader.getRoot();
            ((Stage) show_all_recipes.getScene().getWindow()).setScene(new Scene(root));
        });
        assert ButtonAddProducts != null : "fx:id=\"ButtonAddProducts\" was not injected: check your FXML file 'sample.fxml'.";
        ButtonAddProducts.setOnAction(event -> {
            FXMLLoader loader = LoadXML.load("ForAddingProducts.fxml");
            Parent root = loader.getRoot();
            ((Stage) ButtonAddProducts.getScene().getWindow()).setScene(new Scene(root));
        });
    }
}

//sample.fxm;