// author: Daniil-Y

package main.Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.Data.Query;
import javafx.scene.control.Button;

public class ControllerForProducts {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label AllProducts;
    @FXML
    private Button cancelButton;
    private String parentPath;
    public void savePath(String path) {
        this.parentPath = path;
    }
    @FXML
    void initialize() {
        assert AllProducts != null : "fx:id=\"AllProducts\" was not injected: check your FXML file 'forButtonProducts.fxml'.";
        try {
            ArrayList<ArrayList<String>> test = Query.getFullInformation("products");
            for(ArrayList<String> s: test){
                AllProducts.setText(AllProducts.getText() + "\n"+ s.toString());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        cancelButton.setOnAction(event -> {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            FXMLLoader loader = LoadXML.load(parentPath);
            Parent root = loader.getRoot();
            ((Stage) cancelButton.getScene().getWindow()).setScene(new Scene(root));
        });
    }
}