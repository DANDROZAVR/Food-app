package main.Application;

import javafx.scene.control.Hyperlink;
import main.Model.Recipes.Recipe;

import java.util.ArrayList;
import java.util.Arrays;

public class Order {
    ArrayList<Hyperlink> content;
    String date;
    public Order(ArrayList<Hyperlink> content,String date){
        this.date = date;
        this.content = new ArrayList<>();
        this.content.addAll(content);
    }
}
