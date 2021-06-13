package main.Model.Products;

public class Drinks extends Product {
    private final boolean sugar;
    private final String colour;

    public Drinks(String name, int calories, int id, boolean sugar, String productGroup, String colour) {
        super(name, calories, id, productGroup);
        this.sugar = sugar;
        this.colour = colour;
    }
    public boolean hasSugar(){ return sugar; }
    public String getColour(){ return colour; }
}
