package main.Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.Data.Query;

public class ControllerForReceipts {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label Receipts;

    @FXML
    void initialize() {
        assert Receipts != null : "fx:id=\"Receipts\" was not injected: check your FXML file 'ForButtonReceipts.fxml'.";
        try {
            ArrayList<ArrayList<String>> test = Query.getFullInformation("receipts");
            for(ArrayList<String> s: test){
                Receipts.setText(Receipts.getText() + "\n"+ s.toString());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
