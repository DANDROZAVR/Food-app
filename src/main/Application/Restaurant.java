package main.Application;

import javafx.util.Pair;
import main.Data.Database;
import main.Data.Parser;
import main.Data.Query;
import main.Model.Recipes.Recipe;

import java.sql.SQLException;
import java.util.ArrayList;

public class Restaurant extends Building{
    public Restaurant(int id, String geoposition, String adres, String name){
        setId(id);
        setGeoposition(geoposition);
        setAdres(adres);
        setName(name);
    }
    public ArrayList<Recipe> getMenu() throws SQLException,Exception {
        ArrayList<ArrayList<String>> Ides = Database.execute("select id_rec from group_meals_content where id_group = (select id_group from restaurants_group_meals where id_restaurant =" + this.getId() +");");
        ArrayList<Recipe> res = new ArrayList<>();
        for(ArrayList<String> recipeId: Ides){
            if(recipeId.get(0).equals("id_rec")){
                continue;
            }
            ArrayList<ArrayList<String>> oneRecipe = Database.execute("select * from recipes where id_rec =" + Integer.parseInt(recipeId.get(0)) +";");
            ArrayList<Pair<Integer,Integer>> content = Query.getAllContentOfRecipe(Integer.parseInt(oneRecipe.get(1).get(0)));
            res.add(Parser.getRecipesFrom(oneRecipe, content).get(0));
        }
        return res;
    }
}
