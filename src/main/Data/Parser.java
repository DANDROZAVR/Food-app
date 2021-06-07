package main.Data;

import javafx.util.Pair;
import main.Model.Restaurants.*;
import main.Model.Products.*;
import main.Model.Restaurants.Restaurant;
import main.Model.Products.Drinks;
import main.Model.Products.Product;
import main.Model.Products.Solids;
import main.Model.Products.Species;
import main.Model.Recipes.Recipe;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class Parser {
    public static int findColumnInRow(String columnName, int row, ArrayList<ArrayList<String>> table) throws IndexOutOfBoundsException, Exception {
        if (row < 0 || row >= table.size()) throw new IndexOutOfBoundsException("Illegal row number [" + row + "]");
        for (int i = 0; i < table.get(row).size(); ++i)
            if (table.get(row).get(i).equals(columnName)) return i;
        throw new Exception("Can't find column with name [" + columnName + "] in table [" + table + "]");
    }

    public static int getRowByIdFromSortedTable(String id, ArrayList<ArrayList<String>> table, int columnIdNumber, int from) throws Exception {
        for (int i = from; i < table.size(); ++i) {
            int compareResult = table.get(i).get(columnIdNumber).compareTo(id);
            if (compareResult == 0) return i;
            if (compareResult > 0) throw new Exception("Table is not sorted by id");
        }
        // can be optimized with bin search
        throw new Exception("Id number is bigger then all content in table");
    }
    public static int getRowById(String id, ArrayList<ArrayList<String>> table, int columnIdNumber) throws Exception {
        for (int i = 0; i < table.size(); ++i) {
            int compareResult = table.get(i).get(columnIdNumber).compareTo(id);
            if (compareResult == 0) return i;
        }
        // can be optimized with bin search
        throw new Exception("Can't found row with the same id");
    }
    public static Recipe parseRecipeById(int id) throws SQLException,Exception {
        ArrayList<ArrayList<String>> temp = Database.execute("select * from recipes where id_rec =" + id +";");
        ArrayList<Pair<Integer,Integer>> content = Query.getAllContentOfRecipe(Integer.parseInt(temp.get(1).get(0)));
        return getRecipesFrom(temp,content).get(0);
    }
    public static Restaurant parseRestaurantById(int id) throws SQLException,Exception {
        ArrayList<ArrayList<String>> temp = Database.execute("select * from restaurants_main where id =" + id +";");
        return getRestaurantsFrom(temp).get(0);
    }
    /*static void main(String...varargs) {

    }*/
    public static ArrayList<Restaurant> getRestaurantsFrom(ArrayList<ArrayList<String>> query)throws Exception {
        ArrayList<Restaurant> result = new ArrayList<>();
        for (int idx = 1; idx < query.size(); idx++) {
            ArrayList<String> row = query.get(idx);
            String id, name, adres, geoposition;
            id = name = adres = geoposition = null;
            for (int j = 0; j < row.size(); ++j) {
                String column = query.get(0).get(j);
                String value = row.get(j);
                switch (column) {
                    case "name" -> name = value;
                    case "id" -> id = value;
                    case "adres" -> adres = value;
                    case "geoposition" -> geoposition = value;
                    default -> throw new Exception("Unknown column find due parsing: " + column);
                }
            }
            result.add(new Restaurant(Integer.parseInt(id), geoposition, adres, name));
        }
        return result;
    }
    public static ArrayList<Recipe> getRecipesFrom(ArrayList<ArrayList<String>> query, ArrayList<Pair<Integer,Integer>> recipesContent) throws Exception {
        ArrayList<Recipe> result = new ArrayList<>();
        for (int idx = 1; idx < query.size(); idx++) {
            ArrayList <String> row = query.get(idx);
            String id, name, sum_weight, sum_calories, description, links;
            id = name = description = sum_weight = sum_calories = links = null;
            for (int j = 0; j < row.size(); ++j) {
                String column = query.get(0).get(j);
                String value = row.get(j);
                switch (column) {
                    case "description" -> description = value;
                    case "name" -> name = value;
                    case "id_rec" -> id = value;
                    case "sum_calories" -> sum_calories = value;
                    case "sum_weight" -> sum_weight = value;
                    case "links" -> links = value;
                    default -> throw new Exception("Unknown column find due parsing: " + column);
                }
            }
            result.add(new Recipe(Integer.parseInt(id),name, description,recipesContent));
            //result.add(new Recipe(Integer.parseInt(id), name, description, Integer.parseInt(sum_calories), Integer.parseInt(sum_weight)));
        }
        return result;
    }
    public static ArrayList<Recipe> getRecipesFrom(ArrayList<ArrayList<String>> query) throws Exception {
        ArrayList<Recipe> result = new ArrayList<>();
        for (int idx = 1; idx < query.size(); idx++) {
            ArrayList <String> row = query.get(idx);
            String id, name, sum_weight, sum_calories, description, links;
            id = name = description = sum_weight = sum_calories = links = null;
            for (int j = 0; j < row.size(); ++j) {
                String column = query.get(0).get(j);
                String value = row.get(j);
                switch (column) {
                    case "description" -> description = value;
                    case "name" -> name = value;
                    case "id_rec" -> id = value;
                    case "sum_calories" -> sum_calories = value;
                    case "sum_weight" -> sum_weight = value;
                    case "links" -> links = value;
                    default -> throw new Exception("Unknown column find due parsing: " + column);
                }
            }
            result.add(new Recipe(Integer.parseInt(id), Integer.parseInt(sum_weight), Integer.parseInt(sum_calories), name, description, links));
            //result.add(new Recipe(Integer.parseInt(id), name, description, Integer.parseInt(sum_calories), Integer.parseInt(sum_weight)));
        }
        return result;
    }
    /**
     *
     * @param query result of some select query
     * @return Parsed ArrayList of Products from sql statement with all additional columns, like fruits, species etc.
     * @throws Exception
     */
    public static ArrayList<Product> getProductsFrom(ArrayList<ArrayList<String>> query) throws Exception {
        ArrayList<Product> result = new ArrayList<>();
        for (ArrayList<String> row : query) {
            String description, name, productType, calories, id, area, productClass, taste, sugar, colour;
            String fat, monounsaturated_fat, protein, carbo, sugar_total, zinc, iron, calcium, magnesium;
            String va, vc, vb6, vb12, vk, ve;
            fat = monounsaturated_fat = protein = calcium = carbo = sugar_total = zinc = iron = calcium = magnesium = null;
            va = vc = ve = vb6 = vb12 = vk = null;
            name = description = productClass = productType = calories = id = area = taste = sugar = colour = null;
            for (int j = 0; j < row.size(); ++j) {
                String column = query.get(0).get(j);
                String value = row.get(j);
                switch (column) {

                    case "fat" -> fat = value;
                    case "protein" -> protein = value;
                    case "productType" -> productType = value;
                    case "carbo" -> carbo = value;
                    case "sugar_total" -> sugar_total = value;
                    case "zinc" -> zinc = value;
                    case "iron" -> iron = value;
                    case "calcium" -> calcium = value;
                    case "magnesium" -> magnesium = value;
                    case "monounsaturated_fat" -> monounsaturated_fat = value;

                    case "vitamin_A" -> va = value;
                    case "vitamin_B6" -> vb6 = value;
                    case "vitamin_B12" -> vb12 = value;
                    case "vitamin_C" -> vc = value;
                    case "vitamin_E" -> ve = value;
                    case "vitamin_K" -> vk = value;

                    case "description" -> description = value;
                    case "name" -> name = value;
                    case "product_group" -> productType = value;
                    case "product_class" -> productClass = value;
                    case "id_prod" -> id = value;
                    case "area" -> area = value;
                    case "calories" -> calories = value;
                    case "sugar" -> sugar = value;
                    case "taste"-> taste = value;
                    case "colour"-> colour = value;
                    //default -> throw new Exception("Unknown column find due parsing: " + column);
                }
            }
            if (id == null)
                id = "0";
            if (name == null)
                name = "";
            if (name == null || productType == null || productClass == null || calories == null)
                throw new Exception("Some of non-null by definition values are null:" + name + ", " + productType + ", " + productClass + ", " + calories + ", " + id);
            Product item = null;

            switch (Objects.requireNonNull(productClass)) {
                case "Drinks":
                    item = new Drinks(name, Integer.parseInt(calories), Integer.parseInt(id), Boolean.parseBoolean(sugar), productType, colour);
                    break;
                case "Solids":
                    Solids itemRet = new Solids(name, Integer.parseInt(calories), Integer.parseInt(id), productType);
                    if (fat != null && monounsaturated_fat != null && protein != null && carbo != null && sugar_total != null)
                        itemRet.setNutrient(new Nutrient(Double.parseDouble(fat), Double.parseDouble(protein), Double.parseDouble(carbo), Double.parseDouble(sugar_total), Double.parseDouble(zinc), Double.parseDouble(iron), Double.parseDouble(calcium), Double.parseDouble(magnesium), Double.parseDouble(monounsaturated_fat)));
                    if (va != null && vb6 != null && vb12 != null && vc != null && vk != null && ve != null)
                        itemRet.setVitamins(new Vitamins(Double.parseDouble(va), Double.parseDouble(vb6), Double.parseDouble(vb12), Double.parseDouble(vc), Double.parseDouble(ve), Double.parseDouble(vk)));
                    item = itemRet;
                    break;
                case "Species":
                    if (taste == null) {
                        System.err.println("Taste is null for [" + name + "]\n");
                        break;
                    }
                    item = new Species(name, Integer.parseInt(calories), Species.getEnumTaste(taste), Integer.parseInt(id), productType);
                    break;
                case "product_class":
                    continue;
                default:
                    throw new Exception("Illegal type of product_class [" + productClass + "] of product [" + row + "]");
            }
            if (item != null && area != null) item.setArea(null); // change to reconvert array.ToString -> Array of Strings
            if (item != null && description != null) item.setDescription(description);
            if (item != null) result.add(item);
        }
        return result;
    }
    public static String getProductClass(Class cl){
        String longName = cl.getName();
        String DoubleName = new String();
        for(int i = longName.length() - 1; i >= 0; i--){
            if(longName.charAt(i) == '.'){
                break;
            }
            DoubleName = longName.charAt(i) + DoubleName;
        }
        return DoubleName;
    }
}