package main.Application;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import main.Data.Parser;
import main.Data.Query;
import main.Model.Others.TimeInterval;
import main.Model.Products.Product;
import main.Model.Recipes.Recipe;
import main.Model.Restaurants.Restaurant;
import main.Model.Shops.Shop;

public class ForAddingShopsController {

    @FXML
    private Button AddShop;

    @FXML
    private Label error_out;

    @FXML
    private TextField GetName;

    @FXML
    private TextField GetDescription;

    @FXML
    private TextField GetAdres;

    @FXML
    private TextField GetGeoposition;

    @FXML
    private ComboBox<String> GetWeekdayStartHour;

    @FXML
    private ComboBox<String> GetWeekdayStartMinute;

    @FXML
    private ComboBox<String> GetWeekdayFinishHour;

    @FXML
    private ComboBox<String> GetWeekdayFinishMinute;

    @FXML
    private ComboBox<String> GetSaturdayStartHour;

    @FXML
    private ComboBox<String> GetSaturdayStartMinute;

    @FXML
    private ComboBox<String> GetSaturdayFinishHour;

    @FXML
    private ComboBox<String> GetSaturdayFinishMinute;

    @FXML
    private ComboBox<String> GetSundayStartHour;

    @FXML
    private ComboBox<String> GetSundayStartMinute;

    @FXML
    private ComboBox<String> GetSundayFinishHour;

    @FXML
    private ComboBox<String> GetSundayFinishMinute;

    @FXML
    private CheckBox GetIsFood;

    @FXML
    private ComboBox<String> GetStars;

    @FXML
    void goToHome(ActionEvent event) {
        Main.goToHome();
    }

    ObservableList<String> AllHours, AllMinutes;

    void setHours(){
        ArrayList<String> List = new ArrayList<>();
        for(int i = 0; i < 24; i++){
            String c = Integer.toString(i);
            if(c.length() == 1){
                c = "0" + c;
            }
            List.add(c);
        }
        AllHours = FXCollections.observableArrayList(List);
    }

    void setMinutes(){
        ArrayList<String> List = new ArrayList<>();
        for(int i = 0; i < 60; i++){
            String c = Integer.toString(i);
            if(c.length() == 1){
                c = "0" + c;
            }
            List.add(c);
        }
        AllMinutes = FXCollections.observableArrayList(List);
    }

    void setBoxes(){
        GetWeekdayStartHour.setValue("");
        GetWeekdayStartHour.setItems(AllHours);
        GetWeekdayStartHour.hide();
        GetWeekdayStartHour.setVisibleRowCount(10);
        GetSaturdayStartHour.setValue("");
        GetSaturdayStartHour.setItems(AllHours);
        GetSaturdayStartHour.hide();
        GetSaturdayStartHour.setVisibleRowCount(10);
        GetSundayStartHour.setValue("");
        GetSundayStartHour.setItems(AllHours);
        GetSundayStartHour.hide();
        GetSundayStartHour.setVisibleRowCount(10);
        GetWeekdayFinishHour.setValue("");
        GetWeekdayFinishHour.setItems(AllHours);
        GetWeekdayFinishHour.hide();
        GetWeekdayFinishHour.setVisibleRowCount(10);
        GetSaturdayFinishHour.setValue("");
        GetSaturdayFinishHour.setItems(AllHours);
        GetSaturdayFinishHour.hide();
        GetSaturdayFinishHour.setVisibleRowCount(10);
        GetSundayFinishHour.setValue("");
        GetSundayFinishHour.setItems(AllHours);
        GetSundayFinishHour.hide();
        GetSundayFinishHour.setVisibleRowCount(10);

        GetWeekdayStartMinute.setValue("");
        GetWeekdayStartMinute.setItems(AllMinutes);
        GetWeekdayStartMinute.hide();
        GetWeekdayStartMinute.setVisibleRowCount(10);
        GetSaturdayStartMinute.setValue("");
        GetSaturdayStartMinute.setItems(AllMinutes);
        GetSaturdayStartMinute.hide();
        GetSaturdayStartMinute.setVisibleRowCount(10);
        GetSundayStartMinute.setValue("");
        GetSundayStartMinute.setItems(AllMinutes);
        GetSundayStartMinute.hide();
        GetSundayStartMinute.setVisibleRowCount(10);
        GetWeekdayFinishMinute.setValue("");
        GetWeekdayFinishMinute.setItems(AllMinutes);
        GetWeekdayFinishMinute.hide();
        GetWeekdayFinishMinute.setVisibleRowCount(10);
        GetSaturdayFinishMinute.setValue("");
        GetSaturdayFinishMinute.setItems(AllMinutes);
        GetSaturdayFinishMinute.hide();
        GetSaturdayFinishMinute.setVisibleRowCount(10);
        GetSundayFinishMinute.setValue("");
        GetSundayFinishMinute.setItems(AllMinutes);
        GetSundayFinishMinute.hide();
        GetSundayFinishMinute.setVisibleRowCount(10);


        ArrayList<String> List = new ArrayList<>();
        List.add("0");List.add("1");List.add("2");List.add("3");List.add("4");List.add("5");
        GetStars.setValue("");
        GetStars.setItems(FXCollections.observableList(List));
        GetStars.hide();
        GetStars.setVisibleRowCount(6);
    }

    void build(){
        setHours();
        setMinutes();
        setBoxes();
    }

    @FXML
    void initialize(){
        build();
        AddShop.setOnAction(event -> {
            try {
                TimeInterval Weekday = new TimeInterval(Integer.parseInt(GetWeekdayStartHour.getValue()), Integer.parseInt(GetWeekdayStartMinute.getValue()), Integer.parseInt(GetWeekdayFinishHour.getValue()), Integer.parseInt(GetWeekdayFinishMinute.getValue()));
                TimeInterval Saturday = new TimeInterval(Integer.parseInt(GetSaturdayStartHour.getValue()), Integer.parseInt(GetSaturdayStartMinute.getValue()), Integer.parseInt(GetSaturdayFinishHour.getValue()), Integer.parseInt(GetSaturdayFinishMinute.getValue()));
                TimeInterval Sunday = new TimeInterval(Integer.parseInt(GetSundayStartHour.getValue()), Integer.parseInt(GetSundayStartMinute.getValue()), Integer.parseInt(GetSundayFinishHour.getValue()), Integer.parseInt(GetSundayFinishMinute.getValue()));
                Query.addNewShop(new Shop(Query.getNewIdFor("shops"), GetGeoposition.getText(), GetAdres.getText(), GetName.getText()), GetDescription.getText(), GetIsFood.isSelected(), Integer.parseInt(GetStars.getValue()), Weekday, Saturday, Sunday);
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
