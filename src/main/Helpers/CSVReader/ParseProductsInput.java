package main.Helpers.CSVReader;


import main.Data.Parser;
import main.Model.Products.Product;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

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
            String file_path = "/utils/productInsert.sql";
            PrintWriter writer = new PrintWriter(new FileWriter(project_path + file_path));
            writer.write("INSERT INTO PRODUCTS(id_prod, product_group, product_class, name, description, calories)\n  VALUES\n");

            int start = 1001;
            for (Product item : info) {
                item.setName(item.getProductType().split(" ")[0]);
                if (start != 1001)
                    writer.println(",");
                writer.printf("(%d, '%s', '%s', '%s', '%s', %d)", start, item.getProductType(), "Solids", item.getName(), item.getDescription(), item.getCalories());
                start += 2;
            }
            writer.write(";");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*public static void main(String[] args) {
        parse();
    }*/
}
