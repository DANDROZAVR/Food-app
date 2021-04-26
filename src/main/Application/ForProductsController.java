// author: Daniil-Y

package main.Application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import jdk.jfr.Event;
import main.Data.Query;
import javafx.scene.control.Button;

public class ForProductsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label AllProducts;
    @FXML
    private Button cancelButton;
    @FXML
    public void goToHome(ActionEvent e) {
        Main.goToHome();
    }
    @FXML
    void initialize() {
        try {
            ArrayList<ArrayList<String>> test = Query.getFullInformation("products");
            for(ArrayList<String> s: test){
                AllProducts.setText(AllProducts.getText() + "\n"+ s.toString());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}