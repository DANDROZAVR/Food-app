package main.Model.Products;

public abstract class Careals extends Product {
    protected Careals(int callories, int id, Solid.ProductTaste taste, boolean isInherited) {
        super(callories, ProductType.Cereals, id, isInherited);
    }
}
