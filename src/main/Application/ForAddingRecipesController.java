package main.Application;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.Data.Parser;
import main.Data.Query;
import main.Model.Products.Product;
import main.Model.Recipes.Recipe;
import javafx.scene.layout.HBox;

public class ForAddingRecipesController {

    @FXML
    private TextField GetName;

    @FXML
    private TextField GetDescription;

    @FXML
    private Button AddRecipe;

    @FXML
    private Button cancelButton;

    @FXML
    private Label error_out;

    @FXML
    private VBox AddRecipes;

    @FXML
    private VBox AddProducts;

    @FXML
    private Button PlusRecipes;

    @FXML
    private Button MinusRecipes;

    @FXML
    private Button PlusProducts;

    @FXML
    private Button MinusProducts;

    ObservableList<String> AllProducts, AllRecipes;

    @FXML
    void goToHome(ActionEvent event) {
        Main.goToHome();
    }

    int SizeRecipes, SizeProducts;

    public void build(){
        SizeRecipes = SizeProducts = 0;
        try{
            ArrayList<String> s = new ArrayList<>();
            ArrayList<Product> products = Parser.getProductsFrom(Query.getFullInformation("Products"));
            for(Product i : products){
                s.add(i.getName());
            }
            AllProducts = FXCollections.observableArrayList(s);
            ArrayList<String> s1 = new ArrayList<>();
            ArrayList<Recipe> recipes = Parser.getRecipesFrom(Query.getFullInformation("Recipes"));
            for(Recipe i : recipes){
                s1.add(i.getName());
            }
            AllRecipes = FXCollections.observableArrayList(s1);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public ChoiceBox<String> getBoxProducts(){
        ChoiceBox<String> _new = new ChoiceBox<String>();
        _new.setMaxHeight(35);
        _new.setValue("");
        _new.setItems(AllProducts);
        return _new;
    }

    public ChoiceBox<String> getBoxRecipes(){
        ChoiceBox<String> _new = new ChoiceBox<String>();
        _new.setMaxHeight(35);
        _new.setValue("");
        _new.setItems(AllRecipes);
        return _new;
    }

    public Label getTextWeight(){
        Label TextWeight = new Label("   Weight(g):");
        TextWeight.setMinWidth(55);
        return TextWeight;
    }

    public TextField ForWeight(){
        TextField TextWeight = new TextField();
        TextWeight.setMaxWidth(70);
        return TextWeight;
    }

    @FXML
    void initialize(){
        build();
        PlusRecipes.setOnAction(event -> {
            HBox _new = new HBox();
            _new.getChildren().add(getBoxRecipes());
            _new.getChildren().add(getTextWeight());
            _new.getChildren().add(ForWeight());
            AddRecipes.getChildren().add(_new);
            SizeRecipes++;
        });
        MinusRecipes.setOnAction(event -> {
            if(SizeRecipes > 0){
                SizeRecipes--;
                AddRecipes.getChildren().remove(SizeRecipes);
            }
        });
        PlusProducts.setOnAction(event -> {
            HBox _new = new HBox();
            _new.getChildren().add(getBoxProducts());
            _new.getChildren().add(getTextWeight());
            _new.getChildren().add(ForWeight());
            AddProducts.getChildren().add(_new);
            SizeProducts++;
        });
        MinusProducts.setOnAction(event -> {
            if(SizeProducts > 0){
                SizeProducts--;
                AddProducts.getChildren().remove(SizeProducts);
            }
        });
        AddRecipe.setOnAction(event -> {

        });
    }
}
