package main.Application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HistoryController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private Button Back;
    void setSceneBack(Scene scene){
        Back.setOnAction(t -> {
            ((Stage) Back.getScene().getWindow()).setScene(scene);
        });
    }
    @FXML
    private VBox history;
    void setRestaurant(ArrayList<Order> content){
            for (Order o : content) {
                Hyperlink temp = new Hyperlink(o.date);
                temp.setOnAction(t2 -> {
                    FXMLLoader loader = LoadXML.load("orderView.fxml");
                    ((OrderViewController) loader.getController()).setOrder(o);
                    ((OrderViewController) loader.getController()).setSceneBack(history.getScene());
                    Parent root = loader.getRoot();
                    ((Stage) history.getScene().getWindow()).setScene(new Scene(root));
                });
                history.getChildren().add(temp);
            }
    }
    @FXML
    void initialize() {
    }
}