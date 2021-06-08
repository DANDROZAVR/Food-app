package main.Helpers.CSVReader;

import javafx.util.Pair;
import main.Data.Parser;
import main.Data.Query;
import main.Model.Products.Product;
import main.Model.Recipes.Recipe;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class ParserRecipesInput {
    public static void parse() {
        try {
            String project_path = new File(".").getCanonicalPath();
            String file_path = "/src/resources/CSV/recipes.csv";
            ArrayList<ArrayList<String>> table = CSVLoad.load(project_path + file_path);
            System.out.println("loaded...");
            assert table != null;
            ArrayList<Recipe> condidates = Parser.getRecipesDangerousFrom(table);
            System.out.println("parsed...");
            ArrayList<Recipe> res = repair(condidates);
            System.out.println("repaired...");
            write(res);
            System.out.println("writed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<Recipe> repair(ArrayList<Recipe> cond) throws Exception {
        ArrayList<Recipe> res = new ArrayList<>();
        Random rand = new Random();
        int clc = 0;
        for (Recipe item : cond) {
            if (item.getName().equals("death by chocolate  copycat dark chocolate sauce")); else continue;
            ++clc;
            int all = 0;
            String[] possibleProducts = item.getIngredients().split("`");
            ArrayList<Pair<Product, Integer>> content = new ArrayList<>();
            ArrayList<Product> Realcontent = new ArrayList<>();
            for (String name : possibleProducts) {
                if (name.length() < 2 || name.equals(", ")) continue;
                ++all;
                //String[] splt = name.split(" ");
                String nameToFind = name;
                /*for (int i = splt.length - 1; i >= 0; --i)
                    if (splt[i].length() > 2) {
                        nameToFind = splt[i];
                        break;
                    }*/
                //if (nameToFind == null) break;
                ArrayList<ArrayList<String>> result_query = Query.getByNamePrefix_all_first("products", nameToFind);
                if (result_query.size() < 2) break;
                ArrayList<Product> similar = Parser.getProductsFrom(result_query);
                if (similar.size() == 0) break;
                Product toAdd = similar.get(0);
                int weight = rand.nextInt(Math.max(70, (int) (300 - toAdd.getCalories() / 2.5)));
                content.add(new Pair<Product, Integer>(toAdd, weight));
                Realcontent.add(toAdd);
            }
            if (content.size() == all) {
                System.out.println(clc);
                res.add(new Recipe(0, item.getName(), item.getDescription(), content, item.getInstruction(), true).setNormalComponent(Realcontent).setTime(item.getTime()));
                //if (res.size() > 5) return res;
            }
        }
        return res;
    }
    public static void write(ArrayList<Recipe> res) {
        try {
            String project_path = new File(".").getCanonicalPath();
            String file_path = "/utils/recipesInsert.sql";
            PrintWriter writer = new PrintWriter(new FileWriter(project_path + file_path));
            writer.println("insert into recipes\n       values");
            int start = 2;
            boolean was = false;
            int actual = 2;

            for (Recipe item : res) {
                if (was)
                    writer.printf(",\n");
                was = true;
                writer.printf("(nextval('for_id_recipes'), '%s', %d, %d, %d, '%s', '%s')", item.getName(), item.getTime(), item.getWeight(), item.getCalories(), item.getDescription(), item.getInstruction());
            }
            writer.println(";\n\n");
            writer.write("insert into recipes_content_products\n  values\n");
            was = false;
            for (Recipe item : res) {
                for (int j = 0; j < item.getComponents().size(); ++j) {
                    if (was)
                        writer.printf(",\n");
                    was = true;
                    writer.printf("(%d, %d, %d, '%s')", actual, item.getComponents().get(j).getKey(), item.getComponents().get(j).getValue(), item.getNormalComponent().get(j).getWeightType());
                }
                actual += 2;
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*public static void main(String[] args) {
        parse();
    }*/
}
