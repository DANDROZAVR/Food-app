package main.Model.Shops;

import javafx.util.Pair;
import main.Application.Order;
import main.Data.Database;
import main.Data.Parser;
import main.Data.Query;
import main.Model.Orders.shopOrder;
import main.Model.Products.Product;
import main.Model.Recipes.Recipe;
import main.Model.Restaurants.Building;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Shop extends Building {
    public Shop(int id, String adres, String name){
        setId(id);
        setAdres(adres);
        setName(name);
    }
    public ArrayList<Integer> getMenu() throws Exception {
        ArrayList<ArrayList<String>> Ides = Database.execute("select id_rec from shops_content_recipes where id_shop =" + getId() + ";");
        Ides.addAll(Database.execute("select id_prod from shops_content_products where id_shop =" + getId() + ";"));
        ArrayList<Integer> res = new ArrayList<>();
        for(ArrayList<String> Id: Ides){
            if(Id.get(0).equals("id_rec") || Id.get(0).equals("id_prod")){
                continue;
            }
           res.add(Integer.parseInt(Id.get(0)));
        }
        return res;
    }
    public static ArrayList<shopOrder> getOrders(int shop) throws SQLException,Exception{
        ArrayList<ArrayList<String>> res = Database.execute("select * from shopOrders where id_shop =" + shop +";");
        ArrayList<shopOrder> ans = new ArrayList<>();
        Map<Integer, ArrayList<Integer>> temp = new HashMap<>();
        Map<Integer, String> dates = new HashMap<>();
        for(ArrayList<String> a: res){
            if(a.get(0).equals("id_order")){
                continue;
            }else{
                if(temp.containsKey(Integer.parseInt(a.get(0)))){
                    temp.get(Integer.parseInt(a.get(0))).add(Integer.parseInt(a.get(2)));
                }else {
                    dates.put(Integer.parseInt(a.get(0)),a.get(3));
                    ArrayList<Integer> temp2 = new ArrayList<>();
                    temp2.add(Integer.parseInt(a.get(2)));
                    temp.put(Integer.parseInt(a.get(0)), temp2);
                }
            }
        }
        for(Integer i: temp.keySet()){
            ans.add(new shopOrder(i,temp.get(i),dates.get(i),Parser.parseShopById(shop))) ;
        }
        return ans;
    }
}
