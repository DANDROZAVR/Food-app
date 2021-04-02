package main.Model.Products;

public abstract class Mushrooms extends Product {
    protected Mushrooms(int callories) {
        super(callories, ProductType.Mushrooms);
    }
}
