package main.Model.Products;

public abstract class Drinks extends Product {
    private final boolean sugar;
    protected Drinks(int callories, int id, boolean isInherited, boolean sugar) {
        super(callories, "Drinks", id, isInherited);
        this.sugar = sugar;
    }
    public boolean hasSugar(){ return sugar; }
}
