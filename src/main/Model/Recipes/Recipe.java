package main.Model.Recipes;

import javafx.util.Pair;
import main.Data.Query;
import main.Model.Products.Product;

import java.sql.SQLException;
import java.util.ArrayList;

public class Recipe {
    private int id, weight, all_calories, time; // All calories
    private String Name = null, description = null, ingredients = null;
    private String instruction;
    private int calories;
    private ArrayList<Pair<Integer, Integer>> Components; // [ id, weight ]
    private ArrayList<Product> NormComp = null;
    public static String fixInstruction(String instruction) {
        StringBuilder res = new StringBuilder();
        boolean start = true;
        int all = 0;
        for (int i = 0; i < instruction.length(); ++i) {
            char c = instruction.charAt(i);
            if (c == '[' || c == ']') continue;
            if (c == '`') {
                if (i + 1 < instruction.length() && instruction.charAt(i + 1) == ',') {
                    res.append("\n");
                    start = true;
                    ++i;
                }
                continue;
            }
            if (start) {
                res.append(++all + ": ");
            }
            start = false;
            res.append(c);
        }
        return res.toString();
    }
    public static String repair(String input) {
        StringBuilder res = new StringBuilder();
        int last = 0;
        int end = 0;
        while(end < input.length()) { //125 max
            int i = end;
            while(end < input.length() && input.charAt(end) != ' ') ++end;
            if (end - last > 115) {
                res.append('\n');
                last = end;
            }
            for (int j = i; j < end; ++j)
                res.append(input.charAt(j));
            if (end != input.length()) res.append(" ");
            ++end;
        }
        return res.toString();
    }
    public Recipe(int id, int calories, String Name, String Description, String Instruction) {
        this.id = id;
        this.Name = Name;
        this.description = Description;
        this.calories = calories;
        //this.weight = weight;
        this.instruction = Instruction;
    }
    public Recipe(int id,  String Name, String Description, ArrayList < Pair <Integer, Integer> > List, String Instruction) throws SQLException {
        this.id = id;
        this.Name = Name;
        this.description = Description;
        this.instruction = Instruction;
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
            Components.add(new_component);
        }
        //this.calories = this.all_calories*100 / this.weight;
    }
    public Recipe(int id,  String Name, String Description, ArrayList < Pair <Product, Integer> > List, String Instruction, boolean ignored) throws SQLException {
        this.id = id;
        this.Name = Name;
        this.description = Description;
        this.instruction = Instruction;
        this.calories = 0;
        this.all_calories = 0;
        this.weight = 0;
        Components = new ArrayList<>();
        for(Pair <Product, Integer> new_component : List) {
            //see is this receipt or product
            int calories = new_component.getKey().getCalories();
            this.all_calories += (calories * new_component.getValue()) / 100;
            this.weight += new_component.getValue();
            Components.add(new Pair<Integer, Integer>(new_component.getKey().getId(), new_component.getValue()));
        }
        this.calories = this.all_calories* 100 / this.weight;
    }
    public Recipe setIngredients(String val) {
        this.ingredients = val;
        return this;
    }
    public Recipe setTime(String time) {
        try {
            this.time = Integer.parseInt(time);
        } catch (Exception e) {
            e.printStackTrace();
            this.time = 0;
        }
        return this;
    }
    public Recipe setTime(int time) {
        this.time = time;
        return this;
    }
    public Recipe setNormalComponent(ArrayList<Product> content) {
        NormComp = content;
        return this;
    }
    public ArrayList<Product> getNormalComponent() {
        return NormComp;
    }
    public String getIngredients() { return ingredients; }

    public int getId(){
        return this.id;
    }
    public ArrayList<Pair<Integer,Integer>> getComponents(){
        return Components;
    }
    public int getWeight(){
        return this.weight;
    }
    public int getTime() {return this.time;}
    public int getAllCalories(){
        return this.all_calories;
    }
    public int getCalories(){
        return this.calories;
    }

    public String getName(){
        return this.Name;
    }
    public String getDescription(){
        return this.description;
    }
    public String getInstruction(){
        return this.instruction;
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
