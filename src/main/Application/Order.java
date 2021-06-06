package main.Application;

import javafx.scene.control.Hyperlink;
import main.Model.Recipes.Recipe;

import java.util.ArrayList;
import java.util.Arrays;

public class Order {
    ArrayList<Recipe> content;
    String date;
    Restaurant restaurant;
    public Order(ArrayList<Recipe> content,String date, Restaurant restaurant){
        this.restaurant = restaurant;
        this.date = date;
        this.content = new ArrayList<>();
        this.content.addAll(content);
    }
}
