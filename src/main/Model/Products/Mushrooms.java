package main.Model.Products;

public abstract class Mushrooms extends Product {
    protected Mushrooms(int callories, int id, Solid.ProductTaste taste, boolean isInherited) {
        super(callories, ProductType.Mushrooms, id, isInherited);
    }
}
