package main.Application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Data.Query;


public class ForRecipesController extends Main {
    @FXML
    private Button ButtonFind;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TextArea textArea;
    @FXML
    private Button cancelButton;
    @FXML
    public void goToHome(ActionEvent e) { goToHome();}
    @FXML
    void openSettings(ActionEvent event) {
    }
    @FXML
    private TextField GetText;
    @FXML
    void initialize() {
        try {
            ArrayList<ArrayList<String>> test = Query.getFullInformation("receipts");
            for(ArrayList<String> s: test){
                textArea.setText(textArea.getText() + "\n"+ s.toString());
            }
            ButtonFind.setOnAction(event -> {
                try{
                    textArea.setText("");
                    ArrayList<ArrayList<String>> output = Query.getByNamePrefix("receipts", GetText.getText());
                    for(ArrayList<String> s: output){
                        textArea.setText(textArea.getText() + "\n"+ s.toString());
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
