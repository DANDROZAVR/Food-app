package main.Model.Products;

import java.util.*;

public abstract class Product {
    final private int callories;
    final private int id;
    protected String name, description, productGroup;
    String[] area, links, tags;

    protected Product(String name, int calories, int id, String productGroup) {
        this.name = name;
        this.callories = calories;
        this.id = id;
        this.productGroup = productGroup;
    }
    public void setDescription(String description){ this.description = description; }
    public void setLinks(String[] links){ this.links = links; }
    public void setArea(String[] area){ this.area = area; }
    public int getCalories(){ return callories; }
    public String getName(){ return name; }
    public String getDescription(){ return description; }
    public String[] getArea(){ return area; }
    public String[] getLinks(){ return links; }
    public String getType() { return productGroup; }

    public static boolean checkParameters(String product_group, String product_class, String name, String description, String area, int calories){
        if(!(1 <= name.length() && name.length() <= 100)){
            return false;
        }
        if(!(1 <= product_group.length() && product_group.length() <= 100)){
            return false;
        }
        List<String> Enums = Arrays.asList("Drinks", "Solids", "Species");
        if(!Enums.contains(product_class)){
            return true;
        }
        return true;
    }
}
