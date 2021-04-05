package main.Model.Products;

public abstract class Product {
    final private int callories;
    final private int id;
    private String productType;
    protected String name = null, description = null;
    String area[] = null;
    protected Product(int calories, String productType, int id, boolean isInherited) {
        this.callories = calories;
        this.productType = productType;
        this.id = id;
    }
    public int getCalories(){ return callories; }
    public String getType() { return productType; }
}
