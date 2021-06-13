package main.Application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Data.Parser;
import main.Data.Query;

public class ButtonShopController {
    private final ListView listView = new ListView();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Back;

    @FXML
    private VBox Vbox;

    @FXML
    private Label Shops;

    void setSceneBack(Scene scene){
        Back.setOnAction(t -> ((Stage) Back.getScene().getWindow()).setScene(scene));
    }
    @FXML
    void initialize(){
        List<Hyperlink> links = new ArrayList<>();
        links.clear();
        Vbox.getChildren().clear();
        listView.getItems().clear();
        try {
            ArrayList<ArrayList<String>> output = Query.getFullInformation("shops_main");
            for (ArrayList<String> s : output) {
                if (!s.get(1).equals("name")) {
                    Hyperlink link = new Hyperlink(s.get(1));
                    link.setTooltip(new Tooltip("adres: " + s.get(2)));
                    link.setOnAction(t -> {
                        FXMLLoader loader = LoadXML.load("ShopsView.fxml");
                        ArrayList<ArrayList<String>> temp = new ArrayList<>();
                        temp.add(output.get(0));
                        temp.add(s);
                        try {
                            ((ShopController) loader.getController()).setShop(Parser.getShopsFrom(temp).get(0));
                            ((ShopController) loader.getController()).setSceneBack(Vbox.getScene());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Parent root = loader.getRoot();
                        ((Stage) Vbox.getScene().getWindow()).setScene(new Scene(root));
                    });
                    links.add(link);
                }
            }
            listView.getItems().addAll(links);
            Vbox.getChildren().addAll(listView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}