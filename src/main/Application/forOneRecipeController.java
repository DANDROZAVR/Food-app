package main.Application;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import main.Data.Database;
import main.Data.Parser;
import main.Data.Query;
import main.Model.Products.Product;
import main.Model.Recipes.Recipe;

import javax.swing.*;

public class forOneRecipeController {
    final ListView listView = new ListView();
    Scene sceneProduct;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea Name;

    @FXML
    private TextArea weight;

    @FXML
    private TextArea calories;

    @FXML
    private TextArea description;

    @FXML
    private VBox link;
    @FXML
    private Button Back;
    void setSceneProduct(Scene sceneProduct){
        this.sceneProduct = sceneProduct;
    }
    void setRecipe(Recipe item) throws SQLException {
        //((TextArea)((HBox)VBoxProduct.getChildren().get(0)).getChildren().get(0)).setAccessibleText(item.getName());
        Name.setText(item.getName());
        weight.setText(String.valueOf(item.getWeight()));
        description.setText(String.valueOf(item.getDescription()));
        calories.setText(String.valueOf(item.getAllCalories()));
        if(item.getComponents() != null) {
            for (Pair<Integer, Integer> c : item.getComponents()) {
                if (c.getKey() % 2 == 1) {
                    ArrayList<ArrayList<String>> component = Database.execute("select * from products where id_prod =" + c.getKey() + ";");
                    Hyperlink temp = new Hyperlink(component.get(1).get(3));
                    temp.setTooltip(new Tooltip("Product group: " + component.get(1).get(1) + "\n" +
                            "Product class: " + component.get(1).get(2) + "\n" + "Calories " + component.get(1).get(5)));
                    temp.setOnAction(t -> {
                        FXMLLoader loader = LoadXML.load("ForOneProductView.fxml");
                        try {
                            ((ForOneProductViewController) loader.getController()).setProduct(Parser.getProductsFrom(component).get(0));
                            ((ForOneProductViewController) loader.getController()).setSceneProduct(Back.getScene());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Parent root = loader.getRoot();
                        ((Stage) Back.getScene().getWindow()).setScene(new Scene(root));
                    });
                    listView.getItems().add(temp);

                }
                if (c.getKey() % 2 == 0) {
                    ArrayList<ArrayList<String>> component = Database.execute("select * from recipes where id_rec =" + c.getKey() + ";");
                    Hyperlink temp = new Hyperlink(component.get(1).get(1));
                    temp.setTooltip(new Tooltip("weight: " + component.get(1).get(2) + "\n" +
                            "Calories per 100 g: " + component.get(1).get(3) + "\n" + "Description: " + component.get(1).get(4)));
                    temp.setOnAction(t -> {
                        FXMLLoader loader = LoadXML.load("forOneRecipe.fxml");
                        try {
                            ArrayList<Pair<Integer, Integer>> content = Query.getAllContentOfRecipe(Integer.parseInt(component.get(1).get(0)));
                            ((forOneRecipeController) loader.getController()).setRecipe(Parser.getRecipesFrom(component, content).get(0));
                            ((forOneRecipeController) loader.getController()).setSceneProduct(Back.getScene());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Parent root = loader.getRoot();
                        ((Stage) Back.getScene().getWindow()).setScene(new Scene(root));
                    });
                    listView.getItems().add(temp);

                }
            }
            link.getChildren().addAll(listView);
        }
    }
    @FXML
    void initialize() {
        Back.setOnAction(t -> {
            ((Stage) Back.getScene().getWindow()).setScene(sceneProduct);
        });
    }
}