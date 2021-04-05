package main.Model.Products;

public abstract class Species extends Product {
    enum Taste {
        Sweet,
        Salty,
        Bitter,
        Spicy
    }
    Taste taste;
    protected Species(int callories, Taste taste, int id, boolean isInherited) {
        super(callories, "Species", id, isInherited);
        this.taste = taste;
    }
}
