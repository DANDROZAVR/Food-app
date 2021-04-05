package main.Model.Products;

public abstract class Fruits extends Solid {
    protected Fruits(int callories, int id, Taste taste, boolean isInherited) {
        super(callories, id, taste, isInherited);
    }
}
// to delete