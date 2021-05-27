package main.Application;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Model.Products.Product;
import main.Model.Recipes.Recipe;

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
    void setRecipe(Recipe item) {
        //((TextArea)((HBox)VBoxProduct.getChildren().get(0)).getChildren().get(0)).setAccessibleText(item.getName());
        Name.setText(item.getName());
        weight.setText(String.valueOf(item.getWeight()));
        description.setText(String.valueOf(item.getDescription()));
        calories.setText(String.valueOf(item.getAllCalories()));
        listView.getItems().add(new Hyperlink(item.getLink()));
        link.getChildren().addAll(listView);
    }
    @FXML
    void initialize() {
        Back.setOnAction(t -> {
            ((Stage) Back.getScene().getWindow()).setScene(sceneProduct);
        });

    }
}