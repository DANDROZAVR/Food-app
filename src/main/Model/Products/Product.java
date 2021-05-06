package main.Model.Products;

public abstract class Product {
    final private int callories;
    final private int id;
    private final String productType;
    protected String name, description;
    String[] area, links;
    protected Product(String name, int calories, String productType, int id) {
        this.name = name;
        this.callories = calories;
        this.productType = productType;
        this.id = id;
    }
    public void setDescription(String description){ this.description = description; }
    public void setLinks(String[] links){ this.links = links; }
    public void setArea(String[] area){ this.area = area; }
    public int getCalories(){ return callories; }
    public String getName(){ return name; }
    public String getDescription(){ return description; }
    public String getProductType(){ return productType; }
    public String[] getArea(){ return area; }
    public String[] getLinks(){ return links; }
    public String getType() { return productType; }
    public static boolean checkParameters(String product_type, String name, String description, String area, int calories){
        if(!(1 <= name.length() && name.length() <= 100)){
            return false;
        }
        if(!(1 <= product_type.length() && product_type.length() <= 100)){
            return false;
        }
        return true;
    }
}
