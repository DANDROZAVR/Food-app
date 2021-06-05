package main.Model.Recipes;

import javafx.util.Pair;
import main.Data.Query;
import main.Model.Products.Product;

import java.sql.SQLException;
import java.util.ArrayList;

public class Recipe {
    private int id, weight, all_calories; // All calories
    private String Name = null, description = null;
    private String Link; // [ id, weight ]

    public Recipe(int id, int weight,int all_calories, String Name, String Description, String Link) {
        this.id = id;
        this.Name = Name;
        this.description = Description;
        this.all_calories = all_calories;
        this.weight = weight;
        this.Link = Link;
    }


    public int getId(){
        return this.id;
    }

    public int getWeight(){
        return this.weight;
    }

    public int getAllCalories(){
        return this.all_calories;
    }

    public String getName(){
        return this.Name;
    }
    public String getDescription(){
        return this.description;
    }
    public String getLink(){
        return this.Link;
    }
    /*
    public void add(Product x, int weight){
        //Interesting that in class product word calories has two l
        int calories = x.getCalories();
        this.all_calories += (calories * weight) / 100;
        this.weight += weight;
        this.Calories = (this.all_calories * this.weight) / 100;
        //Components.add(new Pair<Integer, Integer>(x.getId(), weight));
    }

    public void add(Recipe x, int weight){
        int calories = x.getCalories();
        this.all_calories += (calories * weight) / 100;
        this.weight += weight;
        this.Calories = (this.all_calories * this.weight) / 100;
        Components.add(new Pair<Integer, Integer>(x.id, weight));
    }

    */

}
