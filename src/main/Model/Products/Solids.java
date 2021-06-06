package main.Model.Products;

public class Solids extends Product {
    public Nutrient nutrient = null;
    public Vitamins vitamins = null;
    public void setNutrient(Nutrient nutrient){ this.nutrient = nutrient; }
    public void setVitamins(Vitamins vitamins){ this.vitamins = vitamins; }
    public Solids(String name, int calories, int id, String productType) {
        super(name, calories, id, productType);
    }

}