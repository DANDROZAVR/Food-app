package main.Model.Products;

public abstract class Solid extends Product {
    enum Taste {
        Sweet,
        Sour,
        Salty,
        Bitter,
        None
    }
    private final Taste taste;
    protected Solid(int calories, int id, Taste taste, boolean isInherited) {
        super(calories, "Solid", id, isInherited);
        this.taste = taste;
    }
    protected Solid(int calories, int id, boolean isInherited) {
        super(calories, "Solid", id, isInherited);
        this.taste = Taste.None;
    }
}