package main.Model.Products;

public abstract class Drinks extends Product {
    private final boolean sugar;
    protected Drinks(int calories, int id, boolean isInherited, boolean sugar) {
        super(calories, "Drinks", id, isInherited);
        this.sugar = sugar;
    }
    public boolean hasSugar(){ return sugar; }
}
