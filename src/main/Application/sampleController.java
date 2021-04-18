// author: Daniil-Y

package main.Application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class Controller {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button show_all_products;

    @FXML
    private Button show_all_receipts;

    @FXML
    void initialize() {
        assert show_all_products != null : "fx:id=\"show_all_products\" was not injected: check your FXML file 'sample.fxml'.";
        show_all_products.setOnAction(event -> {
            FXMLLoader loader = LoadXML.load("forProducts.fxml");
            ControllerForProducts ctr = loader.getController();
            //ctr.savePath("/main/Application/sample.fxml");
            Parent root = loader.getRoot();
            ((Stage) show_all_products.getScene().getWindow()).setScene(new Scene(root));
        });
        assert show_all_receipts != null : "fx:id=\"show_all_receipts\" was not injected: check your FXML file 'sample.fxml'.";
        show_all_receipts.setOnAction(event -> {
            FXMLLoader loader = LoadXML.load("forReceipts.fxml");
            Parent root = loader.getRoot();
            ((Stage) show_all_receipts.getScene().getWindow()).setScene(new Scene(root));
        });
    }
}

//sample.fxm;