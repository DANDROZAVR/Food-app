package main.Application;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import main.Data.Database;
import main.Data.Parser;
import main.Data.Query;
import main.Model.Products.Product;
import main.Model.Recipes.Recipe;

public class ForAddingRecipesController extends Main {

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

    @FXML
    private TextField GetTimeHours;

    @FXML
    private TextField GetTimeMinutes;

    ObservableList<String> AllProducts, AllRecipes;

    @FXML
    void goToHome(ActionEvent event) {
        Main.goToHome();
    }

    @FXML
    private TextField GetInstruction;

    @FXML
    private TextField GetTagText;

    @FXML
    private ComboBox<String> GetTag;

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

    public void set_tags(){
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

    public ComboBox<String> getBoxProducts(){
        ComboBox<String> _new = new ComboBox<String>();
        _new.setMaxHeight(35);
        _new.hide();
        _new.setVisibleRowCount(15);
        _new.setValue("");
        _new.setItems(AllProducts);
        return _new;
    }

    public ComboBox<String> getBoxRecipes(){
        ComboBox<String> _new = new ComboBox<String>();
        _new.setMaxHeight(35);
        _new.hide();
        _new.setVisibleRowCount(15);
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
        set_tags();
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
            try {
                int Id = Query.getNewIdFor("recipes");

                ArrayList<Pair<Product, Integer>> list_of_products = new ArrayList<Pair<Product, Integer>>();
                for(Node h: AddProducts.getChildren()){
                    HBox temp = (HBox) h;
                    String Name = ((ComboBox<String>)temp.getChildren().get(0)).getValue();
                    String Weight = ((TextField)temp.getChildren().get(2)).getText();
                    list_of_products.add(new Pair<>(Parser.getProductsFrom(Query.getByName("products", Name)).get(0), Integer.parseInt(Weight)));
                }


                ArrayList<Pair<Recipe, Integer>> list_of_recipes = new ArrayList<Pair<Recipe, Integer>>();
                for(Node h: AddRecipes.getChildren()){
                    HBox temp = (HBox) h;
                    String Name = ((ComboBox<String>)temp.getChildren().get(0)).getValue();
                    String Weight = ((TextField)temp.getChildren().get(2)).getText();
                    list_of_recipes.add(new Pair<>(Parser.getRecipesFrom(Query.getByName("recipes", Name)).get(0), Integer.parseInt(Weight)));
                }

                ArrayList <Pair<Integer, Integer> > List_of_elements = new ArrayList<>();

                for(Pair<Recipe, Integer> i : list_of_recipes){
                    List_of_elements.add(new Pair<>(i.getKey().getId(), i.getValue()));
                }
                for(Pair<Product, Integer> i : list_of_products){
                    List_of_elements.add(new Pair<>(i.getKey().getId(), i.getValue()));
                }

                Recipe new_recipe = new Recipe(Id, GetName.getText(), GetDescription.getText(), List_of_elements, GetInstruction.getText());
                Integer hours = 0, minutes = 0;
                if(GetTimeHours.getText() != ""){
                    hours = Integer.parseInt(GetTimeHours.getText());
                }
                if(GetTimeMinutes.getText() != ""){
                    minutes = Integer.parseInt(GetTimeMinutes.getText());
                }
                new_recipe.setTime(Integer.toString(hours * 60 + minutes));

                if(GetTag.getValue() != "" && GetTagText.getText() != ""){
                    throw new Exception();
                }
                if(GetTag.getValue() == "" && GetTagText.getText() == ""){
                    throw new Exception();
                }
                String Tagg = new String();
                if(GetTag.getValue() != ""){
                    Tagg = GetTag.getValue();
                }else{
                    Tagg = GetTagText.getText();
                }
                Query.addNewRecipe(new_recipe, list_of_recipes, list_of_products, Tagg);
                error_out.setTextFill(Color.web("#16b221", 0.8));
                error_out.setText("OK");
            }catch (Exception e){
                error_out.setTextFill(Color.web("#dd0e0e", 0.8));
                error_out.setText("ERROR");
                e.printStackTrace();
            }
        });
    }
}
