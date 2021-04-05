package main.Model.Products;

public abstract class Mushrooms extends Solid {
    protected Mushrooms(int calories, int id, Taste taste, boolean isInherited) {
        super(calories, id, taste, isInherited);
    }
}
// to delete