package main.Application;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import main.Data.Parser;
import main.Data.Query;
import main.Model.Recipes.Recipe;

public class RestaurantsController {
    static Map<Integer, ArrayList<Order>> content;
    Map<Hyperlink, Recipe> helper;
    private final ListView listView = new ListView();
    @FXML
    private ResourceBundle resources;
    @FXML
    private Button Back;
    @FXML
    private URL location;
    @FXML
    private VBox VBoxOrder;
    @FXML
    private Button order;
    @FXML
    private Button history;

    void setRestaurant(Restaurant restaurant)  throws Exception {
        helper = new HashMap<>();
        ArrayList<Recipe> menu = restaurant.getMenu();
        List<Hyperlink> links = new ArrayList<>();
        links.clear();
        Vbox.getChildren().clear();
        listView.getItems().clear();
        for (Recipe res : menu) {
            Hyperlink link = new Hyperlink(res.getName());
            helper.put(link, res);
            link.setTooltip(new Tooltip("weight: " + res.getWeight() + "\n" +
                    "All calories: " + res.getAllCalories()+ "\n" + "Description: " + res.getDescription()));
            link.setOnAction(t -> {
                FXMLLoader loader = LoadXML.load("ForOneRecipe.fxml");
                try {
                    ((forOneRecipeController) loader.getController()).setRecipe(res);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                ((forOneRecipeController) loader.getController()).setSceneProduct(Vbox.getScene());
                Parent root = loader.getRoot();
                ((Stage) link.getScene().getWindow()).setScene(new Scene(root));
            });
            links.add(link);
        }
        ArrayList<Recipe> order1 = new ArrayList<>();
        for(Hyperlink l: links){
            HBox temp1 = new HBox();
            Button add = new Button("add");
            temp1.getChildren().addAll(l,add);
            Vbox.getChildren().addAll(temp1);
            add.setOnAction(t1 -> {
                Vbox.getChildren().remove(temp1);
                HBox temp2 = new HBox();
                Button delete = new Button("delete");
                temp2.getChildren().addAll(l,delete);
                order1.add(helper.get(l));
                VBoxOrder.getChildren().add(temp2);
                delete.setOnAction(t2 -> {
                    order1.remove(helper.get(l));
                    temp1.getChildren().remove(add);
                    temp1.getChildren().addAll(l,add);
                    VBoxOrder.getChildren().remove(temp2);
                    Vbox.getChildren().add(temp1);
                });
            });
        }
        order.setOnAction(t -> {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            if(content == null){
                content = new HashMap<>();
            }
            if(!content.containsKey(restaurant)){
                ArrayList<Order> temp = new ArrayList<>();
                temp.add(new Order(order1, dateFormat.format(date),restaurant));
                content.put(restaurant.getId(), temp);
            }else {
                content.get(restaurant).add(new Order(order1, dateFormat.format(date),restaurant));
            }
            try {
                setRestaurant(restaurant);
            } catch (Exception e) {
                e.printStackTrace();
            }
            VBoxOrder.getChildren().clear();
        });
        history.setOnAction(t -> {
            FXMLLoader loader = LoadXML.load("history.fxml");
            if(content != null && content.get(restaurant.getId())!= null){
                System.out.println(content.get(restaurant.getId()));
                ((HistoryController) loader.getController()).setRestaurant(content.get(restaurant.getId()));
                ((HistoryController) loader.getController()).setSceneBack(Vbox.getScene());
                Parent root = loader.getRoot();
                ((Stage) Vbox.getScene().getWindow()).setScene(new Scene(root));
            }else{
                System.out.println("Empty");
            }
        });
    }
    void setSceneBack(Scene scene){
        Back.setOnAction(t -> {
            ((Stage) Back.getScene().getWindow()).setScene(scene);
        });
    }
    @FXML
    private VBox Vbox;

    @FXML
    void initialize() {
    }
}
