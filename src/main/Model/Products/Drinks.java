package main.Model.Products;

public class Drinks extends Product {
    private final boolean sugar;
    public Drinks(String name, int calories, int id, boolean sugar) {
        super(name, calories, "Drinks", id);
        this.sugar = sugar;
    }
    public boolean hasSugar(){ return sugar; }
}
