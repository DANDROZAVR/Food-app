package main.Application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.Data.Query;

public class ForRecipesController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label ShowReceipts;
    @FXML
    private Button cancelButton;
    @FXML
    public void goToHome(ActionEvent e) { Main.goToHome(); }
    @FXML
    void initialize() {
        try {
            ArrayList<ArrayList<String>> test = Query.getFullInformation("receipts");
            for(ArrayList<String> s: test){
                ShowReceipts.setText(ShowReceipts.getText() + "\n"+ s.toString());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}