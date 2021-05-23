package main.Model.Products;

public class Species extends Product {
    enum Taste {
        Sweet,
        Salty,
        Bitter,
        Spicy,
        None
    }
    Taste taste;
    public Species(String name, int calories, Taste taste, int id) {
        super(name, calories, "Species", id);
        this.taste = taste;
    }
    public static Taste getEnumTaste(String taste) {
        if(taste == null){
            System.err.println("taste is null");
        }else {
            switch (taste) {
                case "Sweet":
                    return Taste.Sweet;
                case "Salty":
                    return Taste.Salty;
                case "Bitter":
                    return Taste.Bitter;
                case "Spicy":
                    return Taste.Spicy;
                default:
                    System.err.println("Can't get taste " + taste);
                    break;
            }
        }
        return Taste.None;
    }
}
