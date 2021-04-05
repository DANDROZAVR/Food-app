package main.Model.Receipts;

import javafx.util.Pair;
import main.Model.Products.Product;

import java.util.ArrayList;

public class Receipt {
    private int id, Calories, weight, all_calories; // All calories
    private String Name = null, description = null;
    private ArrayList<Pair<Integer, Integer>> Components; // [ id, weight ]

    public Receipt(int id,  String Name, String Description, ArrayList < Pair <Integer, Integer> > List){
        this.id = id;
        this.Name = Name;
        this.description = Description;
        this.Calories = 0;
        this.all_calories = 0;
        this.weight = 0;
        for(Pair <Integer, Integer> new_component : List) {
            //see is this receipt or product
            if((new_component.getKey() & 1) == 1) {
                int calories = Receipt.getCalories(new_component.getKey());
                this.all_calories += (calories * new_component.getValue()) / 100;
                this.weight += new_component.getValue();
            }

            if((new_component.getKey() & 1) == 0) {
                /* //Need Product.getCalories(int id);
                int calories = Product.getCalories(new_component.getKey());
                this.all_calories += (calories * new_component.getValue()) / 100;
                */
                this.weight += new_component.getValue();
            }
            this.Calories = (this.all_calories * this.weight) / 100;
            Components.add(new_component);
        }
    }

    //remake this
    static public int getCalories(int id){
        return 0;
    }

    public int getCalories(){
        return this.Calories;
    }

    public int getId(){
        return this.id;
    }

    public int getWeight(){
        return this.weight;
    }

    /*

    public void add(Product x, int weight){
        //Interesting that in class product word calories has two l
        int calories = x.getCallories();
        this.all_calories += (calories * weight) / 100;
        this.weight += weight;
        this.Calories = (this.all_calories * this.weight) / 100;
        //Components.add(new Pair<Integer, Integer>(x.getId(), weight));
    }

    public void add(Receipt x, int weight){
        int calories = x.getCalories();
        this.all_calories += (calories * weight) / 100;
        this.weight += weight;
        this.Calories = (this.all_calories * this.weight) / 100;
        Components.add(new Pair<Integer, Integer>(x.id, weight));
    }

    */

}
