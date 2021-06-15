package main.Helpers.CSVReader;


import main.Data.Parser;
import main.Data.Query;
import main.Model.Products.Product;
import main.Model.Products.Solids;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ParseProductsInput {
    public static void parse() {
        try {
            String project_path = new File(".").getCanonicalPath();
            String file_path = "/src/resources/CSV/food2r.csv";
            ArrayList<ArrayList<String>> table = CSVLoad.load(project_path + file_path);
            assert table != null;
            table.get(0).add("product_class");
            for (ArrayList<String> item : table)
                item.add("Solids");
            ArrayList<Product> res = Parser.getProductsFrom(table);
            write(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void write(ArrayList<Product> info) {
        try {
            String project_path = new File(".").getCanonicalPath();
            String file_path = "/utils/solidsInsert.sql";
            PrintWriter writer = new PrintWriter(new FileWriter(project_path + file_path));
            writer.write("INSERT INTO solids_full(id_prod, product_group, product_class, name, description, calories, fat, saturated_fat, protein, carbo, sugar, zinc, iron, calcium, magnesium, " +
                    "vitamin_A, vitamin_B6, vitamin_B12, vitamin_C, vitamin_E, vitamin_K)\n  VALUES\n");

            boolean was = false;
            HashMap<String, Integer> mp = new HashMap<>();
            for (Product prod : info) {
                if (!(prod instanceof Solids)) throw new Exception("Product there should be instance of Solids class");
                Solids item = (Solids) prod;
                item.setName(item.getProductType().split(" ")[0]);
                if (item.getName().equals("FAST"))
                    item.setName(item.getDescription().split(",")[1]);
                if (item.getName().toLowerCase().equals("mcdonalds")) {
                    String [] desc = item.getDescription().split(",")[1].split(" ");
                    item.setName(desc[desc.length - 1]);
                }
                item.setName(item.getName().toLowerCase());
                System.out.println(item.getName());
                if (item.nutrient.getCarbo() < item.nutrient.getSugar()) continue;
                if (item.nutrient.getCarbo() + item.nutrient.getFat() + item.nutrient.getProtein() > 100.0) continue;
                mp.putIfAbsent(item.getName(), 0);
                if (mp.get(item.getName()) > 0) continue;
                mp.put(item.getName(), mp.get(item.getName()) + 1);
                if (was)
                    writer.println(",");
                was = true;

                writer.printf("(nextval('for_id_products'), '%s', '%s', '%s', '%s', %d, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)", item.getProductType(), "Solids", item.getName(), item.getDescription(), item.getCalories(),
                        item.nutrient.getFat(), item.nutrient.getsaturated_fat(), item.nutrient.getProtein(), item.nutrient.getCarbo(), item.nutrient.getSugar(), item.nutrient.getZinc(),
                        item.nutrient.getIron(), item.nutrient.getCalcium(), item.nutrient.getMagnesium(), item.vitamins.a, item.vitamins.b6, item.vitamins.b12, item.vitamins.c, item.vitamins.e,
                        item.vitamins.k);
            }
            writer.write(";");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Generated  solids");
    }
    /*public static void main(String[] args) {
        parse();
    }*/
}
