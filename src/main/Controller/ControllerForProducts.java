// author: Daniil-Y

package main.Controller;


import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.Data.Query;

public class ControllerForProducts {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label AllProducts;

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

    }
}