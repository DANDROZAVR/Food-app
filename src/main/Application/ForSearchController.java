package main.Application;

import main.Data.Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import main.Data.Parser;
import main.Data.Query;
import main.Model.Products.Product;
import main.Model.Recipes.Recipe;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class ForSearchController extends Main {

    @FXML
    private VBox AllTags;
    @FXML
    private Button PlusTags;
    @FXML
    private Button MinusTags;
    @FXML
    private CheckBox cCalories;
    @FXML
    private CheckBox cName;
    @FXML
    private CheckBox cId;
    @FXML
    private CheckBox cCategory;

    @FXML
    private CheckBox cAsc;

    @FXML
    private CheckBox cDesc;
    @FXML
    private ImageView home;
    @FXML
    private TextField GetText;
    @FXML
    private Button ButtonFind;
    @FXML
    private Button cancelButton;

    @FXML
    private Button settings;
    @FXML
    void goToHome(ActionEvent event) {
        Main.goToHome();
    }
    @FXML
    void openSettings(ActionEvent event) {
    }
    @FXML
    void goToHome(javafx.event.ActionEvent event) { Main.goToHome(); }
    @FXML
    private TextField GetInstruction;
    @FXML
    int SizeTags;
    @FXML
    ObservableList<String> Tags, Groups;
    @FXML
    public void build(){
        SizeTags = 0;
        try{
            ArrayList<String> s = new ArrayList<>();
            ArrayList<ArrayList<String>> tagsList = Query.getFullInformation("tags");
            for(ArrayList<String> item : tagsList){
                if (!item.get(0).equals("id_tag")) {
                    s.add(item.get(1));
                }
            }
            Tags = FXCollections.observableArrayList(s);
            SizeTags = s.size();
        } catch (Exception e){
            e.printStackTrace();
        }
        try {
            ArrayList<String> s = new ArrayList<>();
            ArrayList<ArrayList<String>> GroupsList = Database.execute("select product_group from products group by product_group order by 1");
            for (ArrayList<String> item : GroupsList)
                if (!item.get(0).equals("product_group")) {
                    s.add(item.get(0));
                }
            Groups = FXCollections.observableArrayList(s);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    public ChoiceBox<String> getBoxTags(){
        ChoiceBox<String> _new = new ChoiceBox<String>();
        _new.setMaxHeight(60);
        _new.setValue("");
        _new.setItems(Tags);
        return _new;
    }
    public TextField getTextField(String prompText) {
        TextField field = new TextField();
        field.setMaxWidth(70);
        field.setPromptText(prompText);
        return field;
    }
    @FXML
    private void setSortingFalse() {
        cCalories.setSelected(false);
        cCategory.setSelected(false);
        cId.setSelected(false);
        cName.setSelected(false);
    };
    @FXML
    void initialize(){
        build();
        PlusTags.setOnAction(event -> {
            HBox _new = new HBox();
            _new.getChildren().add(getBoxTags());
            //_new.getChildren().add(getTextField("From"));
            AllTags.getChildren().add(_new);
            SizeTags++;
        });
        MinusTags.setOnAction(event -> {
            if(SizeTags > 0){
                SizeTags--;
                AllTags.getChildren().remove(SizeTags);
            }
        });
        cCategory.setOnAction(event -> {
            setSortingFalse();
            cCategory.setSelected(true);
        });
        cName.setOnAction(event -> {
            setSortingFalse();
            cName.setSelected(true);
        });
        cId.setOnAction(event -> {
            setSortingFalse();
            cId.setSelected(true);
        });
        cCalories.setOnAction(event -> {
            setSortingFalse();
            cCalories.setSelected(true);
        });
        cAsc.setOnAction(event -> {
            cAsc.setSelected(true);
            cDesc.setSelected(false);
        });
        cDesc.setOnAction(event -> {
            cAsc.setSelected(false);
            cDesc.setSelected(true);
        });
        /*AddRecipe.setOnAction(event -> {
            try {
                int Id = Query.getNewIdFor("Recipes");

                ArrayList<Pair<Product, Integer>> list_of_products = new ArrayList<Pair<Product, Integer>>();
                for(Node h: AddProducts.getChildren()){
                    HBox temp = (HBox) h;
                    String Name = ((ChoiceBox<String>)temp.getChildren().get(0)).getValue();
                    String Weight = ((TextField)temp.getChildren().get(2)).getText();
                    list_of_products.add(new Pair<>(Parser.getProductsFrom(Query.getByName("products", Name)).get(0), Integer.parseInt(Weight)));
                }


                ArrayList<Pair<Recipe, Integer>> list_of_recipes = new ArrayList<Pair<Recipe, Integer>>();
                for(Node h: AddRecipes.getChildren()){
                    HBox temp = (HBox) h;
                    String Name = ((ChoiceBox<String>)temp.getChildren().get(0)).getValue();
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
                Query.addNewRecipe(new_recipe, list_of_recipes, list_of_products);
                error_out.setTextFill(Color.web("#16b221", 0.8));
                error_out.setText("OK");
            }catch (Exception e){
                error_out.setTextFill(Color.web("#dd0e0e", 0.8));
                error_out.setText("ERROR");
                e.printStackTrace();
            }
        });*/
    }
}

