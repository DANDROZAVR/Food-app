package main.Model.Products;

public abstract class Drinks extends Product {
    protected Drinks(int callories, int id, Solid.ProductTaste taste, boolean isInherited) {
        super(callories, ProductType.Drinks, id, isInherited);
    }
}
