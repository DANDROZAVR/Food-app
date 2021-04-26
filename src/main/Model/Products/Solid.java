package main.Model.Products;

public class Solid extends Product {
    enum Taste {
        Sweet,
        Sour,
        Salty,
        Bitter,
        None
    }
    private final Taste taste;
    public Solid(String name, int calories, int id, Taste taste) {
        super(name, calories, "Solid", id);
        this.taste = taste;
    }
    public Solid(String name, int calories, int id) {
        super(name, calories, "Solid", id);
        this.taste = Taste.None;
    }
    public static Taste getEnumTaste(String s) {
        switch (s) {
            case "Sweet":
                return Taste.Sweet;
            case "Sour":
                return Taste.Sour;
            case "Salty":
                return Taste.Salty;
            case "Bitter":
                return Taste.Bitter;
            case "None":
                return Taste.None;
            default:
                System.err.println("Can't get taste " + s);
                break;
        }
        return Taste.None;
    }
}