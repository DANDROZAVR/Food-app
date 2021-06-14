package main.Application;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Model.Recipes.Recipe;

public class OrderViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox Content;

    void setOrder(Order o){
        Content.getChildren().clear();
        for(Recipe r: o.content){
            Hyperlink link = new Hyperlink(r.getName());
            link.setTooltip(new Tooltip("weight: " + r.getWeight() + "\n" +
                        "All calories: " + r.getAllCalories()+ "\n" + "Description: " + r.getDescription()));
            link.setOnAction(t -> {
                FXMLLoader loader = LoadXML.load("forOneRecipe.fxml");
                try {
                    ((forOneRecipeController) loader.getController()).setRecipe(r);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                ((forOneRecipeController) loader.getController()).setSceneProduct(Content.getScene());
                Parent root = loader.getRoot();
                ((Stage) link.getScene().getWindow()).setScene(new Scene(root));
            });
            Content.getChildren().add(link);
        }
    }
    @FXML
    private Button Back;
    void setSceneBack(Scene scene){
        Back.setOnAction(t -> {
                ((Stage) Back.getScene().getWindow()).setScene(scene);
        });
    }
    @FXML
    void initialize() {}
}