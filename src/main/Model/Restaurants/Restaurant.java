package main.Model.Restaurants;

import javafx.util.Pair;
import main.Application.Order;
import main.Data.Database;
import main.Data.Parser;
import main.Data.Query;
import main.Model.Recipes.Recipe;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Restaurant extends Building{
    public Restaurant(int id, String geoposition, String adres, String name){
        setId(id);
        setGeoposition(geoposition);
        setAdres(adres);
        setName(name);
    }
    public ArrayList<Recipe> getMenu() throws SQLException,Exception {
        ArrayList<ArrayList<String>> Ides = Database.execute("select id_rec from restaurant_content_recipes where id_restaurant =" + this.getId() +";");
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
    public static ArrayList<Order> getOrders(int restaurant) throws Exception{
        ArrayList<ArrayList<String>> res = Database.execute("select * from restaurant_orders where id_restaurant =" + restaurant +";");
        ArrayList<Order> ans = new ArrayList<>();
        Map<Integer, ArrayList<Recipe>> temp = new HashMap<>();
        Map<Integer, String> dates = new HashMap<>();
        for(ArrayList<String> a: res){
            if(a.get(0).equals("id_order")){
                continue;
            }else{
                if(temp.containsKey(Integer.parseInt(a.get(0)))){
                    temp.get(Integer.parseInt(a.get(0))).add(Parser.parseRecipeById(Integer.parseInt(a.get(2))));
                }else {
                    dates.put(Integer.parseInt(a.get(0)),a.get(4));
                    ArrayList<Recipe> temp2 = new ArrayList<>();
                    temp2.add(Parser.parseRecipeById(Integer.parseInt(a.get(2))));
                    temp.put(Integer.parseInt(a.get(0)), temp2);
                }
            }
        }
        for(Integer i: temp.keySet()){
            ans.add(new Order(i,temp.get(i),dates.get(i),Parser.parseRestaurantById(restaurant))) ;
        }
        return ans;
    }
}