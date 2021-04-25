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
    private Button show_all_receipts;
    @FXML
    void openSettings(ActionEvent event) {
    }
    @FXML
    void goToHome(ActionEvent event) {
    }
    @FXML
    void initialize() {
        assert show_all_products != null : "fx:id=\"show_all_products\" was not injected: check your FXML file 'sample.fxml'.";
        show_all_products.setOnAction(event -> {
            FXMLLoader loader = LoadXML.load("ForProducts.fxml");
            ForProductsController ctr = loader.getController();
            Parent root = loader.getRoot();
            ((Stage) show_all_products.getScene().getWindow()).setScene(new Scene(root));
        });
        assert show_all_receipts != null : "fx:id=\"show_all_receipts\" was not injected: check your FXML file 'sample.fxml'.";
        show_all_receipts.setOnAction(event -> {
            FXMLLoader loader = LoadXML.load("ForReceipts.fxml");
            Parent root = loader.getRoot();
            ((Stage) show_all_receipts.getScene().getWindow()).setScene(new Scene(root));
        });
    }
}

//sample.fxm;