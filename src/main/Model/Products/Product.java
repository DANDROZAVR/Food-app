package main.Model.Products;

import java.util.*;

public abstract class Product {
    final private int callories;
    final private int id;
    protected String name, description, productGroup, weightType;
    String[] area, links, tags;
    private String repair(String input) {
        StringBuilder res = new StringBuilder();
        int last = 0;
        int end = 0;
        while(end < input.length()) { //125 max
            int i = end;
            while(end < input.length() && input.charAt(end) != ' ') ++end;
            if (end - last > 123) {
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
    protected Product(String name, int calories, int id, String productGroup) {
        this.name = name.toLowerCase();
        this.callories = calories;
        this.id = id;
        this.productGroup = productGroup;
        if (!name.equals("watermelon") && (name.contains("milk") || name.contains("water") || name.contains("shake") || name.contains("juice")))
            weightType = "ml"; else weightType = "g";
    }
    public void setDescription(String description){ this.description = repair(description); }
    public void setLinks(String[] links){ this.links = links; }
    public void setArea(String[] area){ this.area = area; }
    public void setName(String name){ this.name = name; }
    public int getCalories(){ return callories; }
    public int getId(){ return id; }
    public String getName(){ return name; }
    public String getDescription(){ return description; }
    public String[] getArea(){ return area; }
    public String[] getLinks(){ return links; }
    public String getType() { return productGroup; }
    public String getProductType() { return productGroup; }
    public String getWeightType() { return weightType; }
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
