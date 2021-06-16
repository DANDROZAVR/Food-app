package main.Application;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.Data.Database;
import main.Data.Parser;
import main.Data.Query;
import main.Helpers.IconFinder.FindIcon;
import main.Model.Products.Product;
import main.Model.Recipes.Recipe;


public class ForAddingProductsController extends Main {

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
    private ImageView product_icon;
    @FXML
    private Button findIcon;
    @FXML
    void tryToFindIcon(ActionEvent event) {
        try {
            System.out.println(FindIcon.loadIconFromNet(GetName.getText()));
            System.out.println("@../../resources/Icons/" + GetName.getText() + ".png");
            Thread.sleep(500);
            product_icon.setImage(new Image("@../../resources/Icons/" + GetName.getText() + ".png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private ComboBox<String> GetTag;

    @FXML
    private TextField GetTagText;

    public void build(){
        try{
            ArrayList<ArrayList<String>> t = Database.execute("select tag from tags;");
            ArrayList<String> s = new ArrayList<>();
            s.add("");
            for(int i = 1; i < t.size(); i++){
                s.add(t.get(i).get(0));
            }
            t = Database.execute("select tag from products_tag;");
            Set<String> st = new TreeSet<>();
            for(int i = 1; i < t.size(); i++){
                st.add(t.get(i).get(0));
            }
            for(String i : st){
                s.add(i);
            }
            GetTag.setMaxHeight(35);
            GetTag.hide();
            GetTag.setVisibleRowCount(15);
            GetTag.setValue("");
            GetTag.setItems(FXCollections.observableArrayList(s));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void initialize(){
        build();
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
                String product_group = GetProductGroup.getText(), product_class = GetProductClass.getValue(), name = GetName.getText(), description = GetDescription.getText(), calories = GetCalories.getText();
                String sugar = getSugar.getText(), colour = getColour.getText(), taste = getTaste.getValue();
                ArrayList< ArrayList<String> > forNewProduct = new ArrayList<>();
                ArrayList<String> row = new ArrayList<String>();
                row.add("id_prod"); row.add("product_group"); row.add("product_class"); row.add("name"); row.add("description"); row.add("calories"); row.add("area"); row.add("taste");
                forNewProduct.add(new ArrayList<>(row));
                row.clear();
                row.add(Integer.toString(Id)); row.add(product_group); row.add(product_class); row.add(name); row.add(description); row.add(calories); row.add(sugar); row.add(taste);
                forNewProduct.add(new ArrayList<>(row));
                row.clear();
                Product _new = Parser.getProductsFrom(forNewProduct).get(0);
                if(GetTag.getValue() != "" && GetTagText.getText() != ""){
                    throw new Exception();
                }
                if(GetTag.getValue() == "" && GetTagText.getText() == ""){
                    throw new Exception();
                }
                String Tagg = new String();
                if(!GetTag.getValue().equals("")){
                    Tagg = GetTag.getValue();
                }else{
                    Tagg = GetTagText.getText();
                }
                Query.addNewProduct(_new, Tagg);
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
