package main.Model.Products;

public abstract class Species extends Product {
    enum Taste {
        Sweet,
        Salty,
        Bitter,
        Spicy
    }
    Taste taste;
    protected Species(int calories, Taste taste, int id, boolean isInherited) {
        super(calories, "Species", id, isInherited);
        this.taste = taste;
    }
}
