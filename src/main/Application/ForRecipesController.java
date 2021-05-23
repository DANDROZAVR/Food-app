package main.Application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Data.Parser;
import main.Data.Query;
import javafx.scene.layout.VBox;


public class ForRecipesController extends Main {
    final ListView listView = new ListView();
    @FXML
    private VBox VBox;
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
        /*Tooltip toolTip = new Tooltip();
        List<Hyperlink> links = new ArrayList<>();
        VBox.getChildren().clear();
        try {
            ArrayList<ArrayList<String>> output = Query.getFullInformation("receipts");
            for (ArrayList<String> s : output) {
                if (!s.get(4).equals("description")) {
                    Hyperlink link = new Hyperlink(s.get(3));
                    link.setTooltip(new Tooltip("Product group: " + s.get(1) + "\n" +
                            "Product class: " + s.get(2)+ "\n" + "Calories " + s.get(5)));
                    link.setOnAction(t -> {
                        FXMLLoader loader = LoadXML.load("ForOneProductView.fxml");
                        ArrayList<ArrayList<String>> temp = new ArrayList<>();
                        temp.add(output.get(0));
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
                }
            }
            listView.getItems().addAll(links);
            VBox.getChildren().addAll(listView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ButtonFind.setOnAction(event -> {
            links.clear();
            VBox.getChildren().clear();
            listView.getItems().clear();
            try {
                ArrayList<ArrayList<String>> output = Query.getFullInformation("receipts");
                for (ArrayList<String> s : output) {
                    if (!s.get(4).equals("description")) {
                        Hyperlink link = new Hyperlink(s.get(3));
                        link.setOnAction(t -> {
                            FXMLLoader loader = LoadXML.load("ForOneProductView.fxml");
                            ArrayList<ArrayList<String>> temp = new ArrayList<>();
                            temp.add(output.get(0));
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
                    }
                }
                listView.getItems().addAll(links);
                VBox.getChildren().addAll(listView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

         */
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
