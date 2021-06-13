package main.Model.Products;

public class Nutrient {
    double fat, saturated_fat, protein, carbo, sugar_total;
    double zinc, iron, calcium, magnesium;
    public Nutrient(double fat, double protein, double carbo, double sugar_total) {
        this.fat = fat;
        this.protein = protein;
        this.carbo = carbo;
        this.sugar_total = sugar_total;
    }
    public Nutrient(double fat, double protein, double carbo, double sugar_total, double zinc, double iron, double calcium, double magnesium, double saturated_fat) {
        this.fat = fat;
        this.protein = protein;
        this.carbo = carbo;
        this.sugar_total = sugar_total;
        this.zinc = zinc;
        this.iron = iron;
        this.calcium = calcium;
        this.magnesium = magnesium;
        this.saturated_fat = saturated_fat;
    }
    public void setFat(double fat) {this.fat = fat;}
    public void setSaturated_fat(double saturated_fat) {this.saturated_fat = saturated_fat;}
    public void setProtein(double protein) {this.protein = protein;}
    public void setCarbo(double carbo) {this.carbo = carbo;}
    public void setSugar(double sugar_total) {this.sugar_total = sugar_total;}

    public void setZinc(double zinc) {this.zinc = zinc;}
    public void setIron(double iron) {this.iron = iron;}
    public void setCalcium(double calcium) {this.calcium = calcium;}
    public void setMagnesium(double magnesium) {this.magnesium = magnesium;}

    public void addFat(double fat) {this.fat += fat;}
    public void addtSaturated_fat(double saturated_fat) {this.saturated_fat += saturated_fat;}
    public void addProtein(double protein) {this.protein += protein;}
    public void addCarbo(double carbo) {this.carbo += carbo;}
    public void addSugar(double sugar_total) {this.sugar_total += sugar_total;}

    public void addZinc(double zinc) {this.zinc += zinc;}
    public void addIron(double iron) {this.iron += iron;}
    public void addCalcium(double calcium) {this.calcium += calcium;}
    public void addMagnesium(double magnesium) {this.magnesium += magnesium;}


    public double getFat() {return fat;}
    public double getsaturated_fat() {return saturated_fat;}
    public double getProtein() {return protein;}
    public double getCarbo() {return carbo;}
    public double getSugar() {return sugar_total;}

    public double getZinc() {return zinc;}
    public double getIron() {return iron;}
    public double getCalcium() {return calcium;}
    public double getMagnesium() {return magnesium;}
}
