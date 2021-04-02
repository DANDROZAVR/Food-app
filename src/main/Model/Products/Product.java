package main.Model.Products;

public abstract class Product {
    final private int callories;
    ProductType type;
    enum ProductType {
        Solid,
        Drinks,
        Species,
        Cereals,
        Mushrooms;
    }
    protected Product(int callories, ProductType type) {
        this.callories = callories;
        this.type = type;
    }
    public int getCallories(){ return callories; }
    public ProductType getType() { return type; }
}
