package main.Application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jdk.jfr.Event;
import main.Data.Parser;
import main.Data.Query;

import javax.swing.*;

public class ForProductsController extends Main {
    final ListView listView = new ListView();
    @FXML
    private ResourceBundle resources;

    @FXML
    private VBox VBox;

    @FXML
    private URL location;

    @FXML
    private Label AllProducts;
    @FXML
    private Button cancelButton;
    @FXML
    public void goToHome(ActionEvent e) {
        goToHome();
    }
    @FXML
    private TextField GetText;
    @FXML
    private Button ButtonFind;
    @FXML
    void initialize() {
        List<Hyperlink> links = new ArrayList<>();
        try {
            ArrayList<ArrayList<String>> test = Query.getFullInformation("products");
            for(ArrayList<String> s: test){
                if(!s.get(4).equals("description")) {
                    Hyperlink link = new Hyperlink(s.get(3));
                    link.setOnAction(t -> {
                        FXMLLoader loader = LoadXML.load("ForOneProductView.fxml");
                        ArrayList<ArrayList<String>> temp = new ArrayList<>();
                        temp.add(test.get(0));
                        temp.add(s);
                        try {
                            ((ForOneProductViewController) loader.getController()).setProduct(Parser.getProductsFrom(temp).get(0));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Parent root = loader.getRoot();
                        ((Stage) GetText.getScene().getWindow()).setScene(new Scene(root));
                    });
                    links.add(link);
                    AllProducts.setText(AllProducts.getText() + "\n" + s.toString());
                }
            }
            listView.getItems().addAll(links);
            VBox.getChildren().addAll(listView);
            ButtonFind.setOnAction(event -> {
                try{
                    AllProducts.setText("");
                    ArrayList<ArrayList<String>> output = Query.getByNamePrefix("products", GetText.getText());
                    for(ArrayList<String> s: output){
                        AllProducts.setText(AllProducts.getText() + "\n"+ s.toString());
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