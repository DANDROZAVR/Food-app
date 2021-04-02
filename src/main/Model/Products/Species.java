package main.Model.Products;

public abstract class Species extends Product {
    protected Species(int callories) {
        super(callories, ProductType.Species);
    }
}
