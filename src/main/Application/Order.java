package main.Application;

import javafx.scene.control.Hyperlink;
import main.Model.Recipes.Recipe;
import main.Model.Restaurants.Restaurant;

import java.util.ArrayList;
import java.util.Arrays;

public class Order {
    ArrayList<Recipe> content;
    String date;
    Restaurant restaurant;
    int id;
    public Order(int id, ArrayList<Recipe> content,String date, Restaurant restaurant){
        this.id = id;
        this.restaurant = restaurant;
        this.date = date;
        this.content = new ArrayList<>();
        this.content.addAll(content);
    }
}
