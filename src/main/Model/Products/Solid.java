package main.Model.Products;

public abstract class Solid extends Product {
    protected Solid(int callories) {
        super(callories, ProductType.Solid);
    }
}