package main.Model.Orders;

import main.Model.Products.Product;
import main.Model.Restaurants.Restaurant;
import main.Model.Shops.Shop;

import java.util.ArrayList;

public class shopOrder {
    ArrayList<Integer> content, prices;
    String date;
    Shop shop;
    int id;
    public shopOrder(int id, ArrayList<Integer> content , String date, Shop shop, ArrayList<Integer> prices){
        this.id = id;
        this.shop = shop;
        this.date = date;
        this.prices = prices;
        this.content = new ArrayList<>();
        this.content.addAll(content);
    }
    public String getDate(){
        return date;
    }
    public ArrayList<Integer> getPrice(){
        return prices;
    }
    public ArrayList<Integer> getContent(){
        return content;
    }
}
