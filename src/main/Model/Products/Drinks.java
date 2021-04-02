package main.Model.Products;

public abstract class Drinks extends Product {
    protected Drinks(int callories) {
        super(callories, Product.ProductType.Drinks);
    }
}
