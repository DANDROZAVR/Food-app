package main.Helpers.Generators;

import javafx.util.Pair;
import main.Data.Query;
import main.Model.Recipes.Recipe;

import java.util.ArrayList;

public class generateRecipe {
    RandomLoad load = null;
    generateRecipe() {
        load = new RandomLoad();
        //load.names();
    }
    public void generate() {
        ArrayList<Pair<Integer, Integer>> content = new ArrayList<>();
        try {
            //Recipe res = new Recipe(Query.getNewIdFor("recipes"), load.getName(), load.getDescription(), content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
