package main.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Data.Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import java.sql.SQLException;
import java.util.ArrayList;

public class ForSearchController extends Main {
    @FXML
    private TextField caloriesTo;
    @FXML
    private TextField caloriesFrom;
    @FXML
    public TextField linesCnt;
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
    public Button settings;
    @FXML
    public void openSettings(javafx.event.ActionEvent actionEvent) {
    }
    @FXML
    void goToHome(javafx.event.ActionEvent event) { Main.goToHome(); }
    @FXML
    private TextField GetInstruction;
    @FXML
    int SizeTags;
    @FXML
    boolean wasSeted = false;
    @FXML
    ObservableList<String> Tags, Groups;
    @FXML
    public void build(){
        SizeTags = 0;
        try{
            ArrayList<String> s = new ArrayList<>();
            ArrayList<ArrayList<String>> tagsList = Database.execute("select tag from products_tag group by 1 order by 1");
            for(ArrayList<String> item : tagsList){
                if (!item.get(0).equals("tag")) {
                    s.add(item.get(0));
                }
            }
            Tags = FXCollections.observableArrayList(s);
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
    public ComboBox<String> getBoxTags(){
        ComboBox<String> _new = new ComboBox<String>();
        _new.setMaxHeight(60);
        _new.setValue("");
        _new.setVisibleRowCount(15);
        _new.setItems(Tags);
        return _new;
    }
    public ComboBox<String> getGroups() {
        ComboBox<String> _new = new ComboBox<>();
        _new.setMaxHeight(60);
        _new.setVisibleRowCount(15);
        _new.setValue("");
        _new.setItems(Groups);
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
    private CheckBox chooseGroup;
    @FXML
    private VBox Allgroups;
    @FXML
    boolean wascName = false;
    @FXML
    boolean wascId = false;
    @FXML
    boolean wascCalories = false;
    @FXML
    boolean wascAsc = false;
    @FXML
    boolean wascDesc = false;
    @FXML
    boolean wascCategory = false;
    @FXML
    private CheckBox cProduct;
    @FXML
    private CheckBox cResipe;
    @FXML
    void initialize(){
        build();
        PlusTags.setOnAction(event -> {
            //_new.getChildren().add(getTextField("From"));
            AllTags.getChildren().add(getBoxTags());
            SizeTags++;
        });
        MinusTags.setOnAction(event -> {
            if(SizeTags > 0){
                SizeTags--;
                AllTags.getChildren().remove(SizeTags);
            }
        });

        cCategory.setOnAction(event -> {
            wascCategory = !wascCategory;
            setSortingFalse();
            cCategory.setSelected(wascCategory);
            cProduct.setSelected(true);
            cResipe.setSelected(false);
        });
        cName.setOnAction(event -> {
            wascName = !wascName;
            setSortingFalse();
            cName.setSelected(wascName);
        });
        cId.setOnAction(event -> {
            wascId = !wascId;
            setSortingFalse();
            cId.setSelected(wascId);
        });
        cCalories.setOnAction(event -> {
            wascCalories = !wascCalories;
            setSortingFalse();
            cCalories.setSelected(wascCalories);
        });
        cAsc.setOnAction(event -> {
            wascAsc = !wascAsc;
            cAsc.setSelected(wascAsc);
            cDesc.setSelected(false);
        });
        cDesc.setOnAction(event -> {
            wascDesc = !wascDesc;
            cAsc.setSelected(false);
            cDesc.setSelected(wascDesc);
        });
        cProduct.setSelected(true);
        cProduct.setOnAction(event -> {
            cProduct.setSelected(true);
            cResipe.setSelected(false);
        });
        cResipe.setOnAction(event -> {
            cProduct.setSelected(false);
            cResipe.setSelected(true);
            cCategory.setSelected(false);
            wascCategory = false;
            if (wasSeted) {
                Allgroups.getChildren().remove(0);
                chooseGroup.setSelected(false);
                wasSeted = false;
            }
        });
        chooseGroup.setOnAction(event -> {
            cProduct.setSelected(true);
            cResipe.setSelected(false);
            if (wasSeted) {
                Allgroups.getChildren().remove(0);
            } else {
                Allgroups.getChildren().add(getGroups());
            }
            wasSeted = true;
        });
        ButtonFind.setOnAction(event -> {
            StringBuilder query = new StringBuilder().append("select * from");
            if (cProduct.isSelected())
                query.append(" products p natural join products_nutrient "); else
                query.append(" recipes p ");
            StringBuilder whereReq = new StringBuilder();
            whereReq.append("where name like '" + GetText.getText() + "%' ");
            if (!caloriesFrom.getText().equals("") || !caloriesTo.getText().equals("")) {
                String first = "0";
                if (!caloriesFrom.getText().equals(""))
                    first = caloriesFrom.getText();
                String second = "1000";
                if (!caloriesTo.getText().equals(""))
                    second = caloriesTo.getText();
                whereReq.append(" and calories>=").append(first).append(" and calories<=").append(second).append(" ");
            }
            if (chooseGroup.isSelected()) {
                String category = ((ComboBox<String>)Allgroups.getChildren().get(0)).getValue();
                whereReq.append(" and ").append("product_group = '").append(category).append("' ");
            }
            for (Node item : AllTags.getChildren()) {
                String Name = ((ComboBox<String>) item).getValue();
                whereReq.append(" and (select count(*) from products_tag t where t.id_prod = p.id_prod and t.tag = '").
                        append(Name).append("' limit 1) > 0 ");
            }
            if (whereReq.toString().length() != 0) {
                query.append(whereReq);
            }
            boolean was = true;
            if (cCategory.isSelected())
                query.append(" order by product_group"); else
            if (cId.isSelected()) {
                query.append(" order by id_");
                if (cProduct.isSelected())
                    query.append("prod"); else
                    query.append("rec");
            } else
            if (cName.isSelected())
                query.append(" order by name"); else
            if (cCalories.isSelected())
                query.append(" order by calories"); else
                was = false;
            if (was) {
                if (cDesc.isSelected())
                    query.append(" desc"); else
                    query.append(" asc");
            }
            query.append(" limit " + linesCnt.getText());
            System.out.println(query.toString());
            FXMLLoader loader = LoadXML.load("ForProducts.fxml");
            Parent root = loader.getRoot();
            ((Stage) cProduct.getScene().getWindow()).setScene(new Scene(root));
            try {
                ((ForProductsController) loader.getController()).setProductFromResult(Database.execute(query.toString()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }
}

