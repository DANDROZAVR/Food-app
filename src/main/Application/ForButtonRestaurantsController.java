package main.Application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import main.Data.Parser;
import main.Data.Query;
import main.Model.Recipes.Recipe;

public class ForButtonRestaurantsController extends Main {
    private final ListView listView = new ListView();
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private VBox Vbox;
    @FXML
    private Button Back;

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
            ArrayList<ArrayList<String>> output = Query.getFullInformation("restaurants_main");
            for (ArrayList<String> s : output) {
                if (!s.get(1).equals("name")) {
                    Hyperlink link = new Hyperlink(s.get(1));
                    link.setTooltip(new Tooltip("adres: " + s.get(3) + "\n" +
                            "Geoposition:  " + s.get(2)+ "\n"));
                    link.setOnAction(t -> {
                        FXMLLoader loader = LoadXML.load("Restaurants.fxml");
                        ArrayList<ArrayList<String>> temp = new ArrayList<>();
                        temp.add(output.get(0));
                        temp.add(s);
                        try {
                            ((RestaurantsController) loader.getController()).setRestaurant(Parser.getRestaurantsFrom(temp).get(0));
                            ((RestaurantsController) loader.getController()).setSceneBack(Vbox.getScene());
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