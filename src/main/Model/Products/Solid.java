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
    protected Solid(int callories, int id, Taste taste, boolean isInherited) {
        super(callories, "Solid", id, isInherited);
        this.taste = taste;
    }
    protected Solid(int callories, int id, boolean isInherited) {
        super(callories, "Solid", id, isInherited);
        this.taste = Taste.None;
    }
}