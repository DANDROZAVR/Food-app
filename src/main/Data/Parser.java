package main.Data;

import javafx.util.Pair;
import main.Model.Products.Drinks;
import main.Model.Products.Product;
import main.Model.Products.Solids;
import main.Model.Products.Species;
import main.Model.Recipes.Recipe;

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


    /*static void main(String...varargs) {

    }*/

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
            name = description = productClass = productType = calories = id = area = taste = sugar = colour = null;
            for (int j = 0; j < row.size(); ++j) {
                String column = query.get(0).get(j);
                String value = row.get(j);
                switch (column) {
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
                    default -> throw new Exception("Unknown column find due parsing: " + column);
                }
            }

            if (name == null || productType == null || productClass == null || calories == null || id == null)
                throw new Exception("Some of non-null by definition values are null: " + name + ", " + productType + ", " + calories + ", " + id);
            Product item = null;
            switch (Objects.requireNonNull(productClass)) {
                case "Drinks":
                    item = new Drinks(name, Integer.parseInt(calories), Integer.parseInt(id), Boolean.parseBoolean(sugar), productType, colour);
                    break;
                case "Solids":
                    item = new Solids(name, Integer.parseInt(calories), Integer.parseInt(id), productType);
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
        String shortName = new String();
        for(int i = longName.length() - 1; i >= 0; i--){
            if(longName.charAt(i) == '.'){
                break;
            }
            shortName = longName.charAt(i) + shortName;
        }
        return shortName;
    }
}