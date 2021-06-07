package main.Model.Products;

public class Vitamins {
    double a, b6, b12, c, e, k;
    public Vitamins(double a, double b6, double b12, double c, double e, double k) {
        this.a = a;
        this.b6 = b6;
        this.b12 = b12;
        this.c = c;
        this.k = k;
    }
    public Vitamins addA(double a) { this.a += a; return this;}
    public Vitamins addC(double c) { this.c += c; return this;}
    public Vitamins addE(double e) { this.e += e; return this;}
    public Vitamins addK(double k) { this.k += k; return this;}
    public Vitamins addB6(double b6) { this.b6 += b6; return this; }
    public Vitamins addb12(double b12) { this.b12 += b12; return this;}
}
