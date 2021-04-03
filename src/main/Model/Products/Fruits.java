package main.Model.Products;

public abstract class Fruits extends Solid {
    protected Fruits(int callories, int id, Solid.ProductTaste taste, boolean isInherited) {
        super(callories, id, taste, isInherited);
    }
}
