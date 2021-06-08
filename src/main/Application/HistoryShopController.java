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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Model.Orders.shopOrder;

public class HistoryShopController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Back;

    @FXML
    private VBox VBox;

    void setSceneBack(Scene scene){
        Back.setOnAction(t -> {
            ((Stage) Back.getScene().getWindow()).setScene(scene);
        });
    }
    void setShop(ArrayList<shopOrder> content){
        for (shopOrder o : content) {
            Hyperlink temp = new Hyperlink(o.getDate());
            temp.setOnAction(t2 -> {
                FXMLLoader loader = LoadXML.load("shopOrderView.fxml");
                try {
                    ((ShopOrderViewController) loader.getController()).setOrder(o);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ((ShopOrderViewController) loader.getController()).setSceneBack(VBox.getScene());
                Parent root = loader.getRoot();
                ((Stage) VBox.getScene().getWindow()).setScene(new Scene(root));
            });
            VBox.getChildren().add(temp);
        }
    }
}