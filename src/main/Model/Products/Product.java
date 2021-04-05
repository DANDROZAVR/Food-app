package main.Model.Products;

public abstract class Product {
    final private int callories;
    final private int id;
    private String productType;
    protected String name = null, description = null;
    String area[] = null;
    protected Product(int callories, String productType, int id, boolean isInherited) {
        this.callories = callories;
        this.productType = productType;
        this.id = id;
    }
    public int getCallories(){ return callories; }
    public String getType() { return productType; }
}
