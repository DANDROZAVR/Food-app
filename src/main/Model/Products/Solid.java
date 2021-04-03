package main.Model.Products;

public abstract class Solid extends Product {
    enum ProductTaste {
        Sweet,
        Sour,
        Salty,
        Bitter,
        None
    }
    private final ProductTaste taste;
    protected Solid(int callories, int id, ProductTaste taste, boolean isInherited) {
        super(callories, ProductType.Solid, id, isInherited);
        this.taste = taste;
    }
}