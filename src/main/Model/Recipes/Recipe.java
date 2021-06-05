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
    private int calories;
    private ArrayList<Pair<Integer, Integer>> Components;

    public Recipe(int id, int weight,int calories, String Name, String Description, String Link) {
        this.id = id;
        this.Name = Name;
        this.description = Description;
        this.calories = calories;
        this.weight = weight;
        this.Link = Link;
    }
    public Recipe(int id,  String Name, String Description, ArrayList < Pair <Integer, Integer> > List) throws SQLException {
        this.id = id;
        this.Name = Name;
        this.description = Description;
        this.calories = 0;
        this.all_calories = 0;
        this.weight = 0;
        Components = new ArrayList<>();
        for(Pair <Integer, Integer> new_component : List) {
            //see is this receipt or product
            if((new_component.getKey() & 1) == 0) {
                int calories = Query.getCaloriesFromRecipes("recipes", new_component.getKey());
                this.all_calories += (calories * new_component.getValue()) / 100;
                this.weight += new_component.getValue();
            }

            if((new_component.getKey() & 1) == 1) {
                int calories = Query.getCaloriesFromProducts("products", new_component.getKey());
                this.all_calories += (calories * new_component.getValue()) / 100;
                this.weight += new_component.getValue();
            }
            this.calories = (this.all_calories * this.weight) / 100;
            Components.add(new_component);
        }
    }


    public int getId(){
        return this.id;
    }
    public ArrayList<Pair<Integer,Integer>> getComponents(){
        return Components;
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
