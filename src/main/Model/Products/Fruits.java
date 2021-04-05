package main.Model.Products;

public abstract class Fruits extends Solid {
    protected Fruits(int calories, int id, Taste taste, boolean isInherited) {
        super(calories, id, taste, isInherited);
    }
}
// to delete