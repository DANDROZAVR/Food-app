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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.Data.Parser;
import main.Data.Query;
import main.Model.Products.Product;

public class ForAddingProductsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField GetName;

    @FXML
    private TextField GetProductGroup;

    @FXML
    private ChoiceBox<String> GetProductClass;

    @FXML
    private TextField GetDescription;

    @FXML
    private TextField GetArea;

    @FXML
    private TextField GetCalories;

    @FXML
    private Button AddProduct;
    @FXML
    public void goToHome(ActionEvent e) { Main.goToHome(); }
    @FXML
    private Button cancelButton;
    @FXML
    private Label error_out;
    @FXML
    private HBox clASSField;
    @FXML
    private void start_GetProductClass(){
        ObservableList<String> GetProductClassList = FXCollections.observableArrayList("Species", "Drinks", "Solids");
        GetProductClass.setValue("");
        GetProductClass.setItems(GetProductClassList);
    }

    @FXML
    void initialize(){

        // Parametrs
        ChoiceBox<String> getTaste = new ChoiceBox<String>();
        {
            getTaste.setMaxHeight(35);
            getTaste.setValue("");
            getTaste.setItems(FXCollections.observableArrayList("Sweet", "Salty", "Bitter", "Sour"));
        }

        Label TextSugar = new Label("Sugar   ");

        Label TextColour = new Label("   Colour   ");

        TextField getSugar = new TextField();
        TextField getColour = new TextField();
        //

        start_GetProductClass();

        GetProductClass.setOnAction(t -> {
            clASSField.getChildren().clear();
            getSugar.clear();
            getColour.clear();
            new ChoiceBox<String>();
            {
                getTaste.setMaxHeight(35);
                getTaste.setValue("");
                getTaste.setItems(FXCollections.observableArrayList("Sweet", "Salty", "Bitter", "Sour"));
            }
            if(GetProductClass.getValue() == "Species"){
                clASSField.getChildren().add(getTaste);
            }
            if(GetProductClass.getValue() == "Drinks"){
                clASSField.getChildren().addAll(TextSugar, getSugar, TextColour, getColour);
            }
        });
        AddProduct.setOnAction(event -> {
            int Id = 0;
            try{
                Id = Query.getNewIdFor("products");
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                String product_group = GetProductGroup.getText(), product_class = GetProductClass.getValue(), name = GetName.getText(), description = GetDescription.getText(), area = GetArea.getText(), calories = GetCalories.getText();
                String sugar = getSugar.getText(), colour = getColour.getText(), taste = getTaste.getValue();
                ArrayList< ArrayList<String> > forNewProduct = new ArrayList<>();
                ArrayList<String> row = new ArrayList<String>();
                row.add("id"); row.add("product_group"); row.add("product_class"); row.add("name"); row.add("description"); row.add("calories"); row.add("area"); row.add("sugar"); row.add("taste");
                forNewProduct.add(new ArrayList<>(row));
                row.clear();
                row.add(Integer.toString(Id)); row.add(product_group); row.add(product_class); row.add(name); row.add(description); row.add(calories); row.add(area); row.add(sugar); row.add(taste);
                forNewProduct.add(new ArrayList<>(row));
                row.clear();
                Product _new = Parser.getProductsFrom(forNewProduct).get(0);
                Query.addNewProduct(_new);
                error_out.setTextFill(Color.web("#16b221", 0.8));
                error_out.setText("OK");
            }catch(Exception e){
                e.printStackTrace();
                error_out.setTextFill(Color.web("#dd0e0e", 0.8));
                error_out.setText("ERROR");
            }
        });
    }
}
