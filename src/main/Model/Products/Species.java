package main.Model.Products;

public abstract class Species extends Product {
    protected Species(int callories, int id, Solid.ProductTaste taste, boolean isInherited) {
        super(callories, ProductType.Species, id, isInherited);
    }
}
