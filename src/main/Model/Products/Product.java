package main.Model.Products;

public abstract class Product {
    final private int callories;
    final private int id;
    private final boolean isInherited;
    ProductType type;
    protected String name = null, description = null;
    String area[] = null;
    enum ProductType {
        Solid,
        Drinks,
        Species,
        Cereals,
        Mushrooms
    }
    protected Product(int callories, ProductType type, int id, boolean isInherited) {
        this.callories = callories;
        this.isInherited = true;
        this.type = type;
        this.id = id;
    }
    public int getCallories(){ return callories; }
    public ProductType getType() { return type; }
}
