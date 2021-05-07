package main.Data;

import main.Model.Products.Drinks;
import main.Model.Products.Product;
import main.Model.Products.Solid;
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

    public static ArrayList<Recipe> getRecipesFrom(ArrayList<ArrayList<String>> table) throws Exception {
        return null;
    }
    /**
     *
     * @param table, from whom sql result Array is returned
     * @return Parsed ArrayList of Products from sql statement with all additional columns, like fruits, species etc.
     * @throws Exception
     */
    public static ArrayList<Product> getProductsFrom(ArrayList<ArrayList<String>> table) throws Exception {
        ArrayList<Product> result = new ArrayList<>();
        //ArrayList<ArrayList<String>> solidsInformation = Query.getFullInformation("solids");
        //ArrayList<ArrayList<String>> drinksInformation = Query.getFullInformation("drinks");
        //ArrayList<ArrayList<String>> speciesInformation = Query.getFullInformation("species");
        //int solidsIndex = 0, drinksIndex = 0, speciesIndex = 0;
        int columnIdNumberSolids, columnIdNumberDrinks, columnIdNumberSpecies;
        columnIdNumberSolids = columnIdNumberDrinks = columnIdNumberSpecies = 0;
        try {
            //  columnIdNumberDrinks = findColumnInRow("id_prod", 0, drinksInformation);
            //  columnIdNumberSolids = findColumnInRow("id_prod", 0, solidsInformation);
            //  columnIdNumberSpecies = findColumnInRow("id_prod", 0, speciesInformation);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            System.err.println(e.getMessage() + ".\nUnknown problem with indexing from table [drinks | solids | species]");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
        //while(columnIdNumberSpecies < solidsInformation.get(0).size() && !solidsInformation.get(0).get(columnIdNumberSpecies).equals("id")) ++columnIdNumberSpecies;
        //while(columnIdNumberDrinks < solidsInformation.get(0).size() && !solidsInformation.get(0).get(columnIdNumberDrinks).equals("id")) ++columnIdNumberDrinks;

        // if (columnIdNumberDrinks == drinksInformation.size() || columnIdNumberSpecies == speciesInformation.size() || columnIdNumberSolids == solidsInformation.size())
        //   throw new Exception("Can't find column id in [solids | species | drinks]");
        for (ArrayList<String> row : table) {
            String description, name, productType, calories, id, area, productClass, taste, sugar;
            name = description = productClass = productType = calories = id = area = taste = sugar = null;
            for (int j = 0; j < row.size(); ++j) {
                String column = table.get(0).get(j);
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
                    case "solid_taste", "species_taste" -> taste = value;
                    default -> throw new Exception("Unknown column find due parsing: " + column);
                }
            }
            /*
            if (productClass.equals("drinks")) {
                while(drinksIndex < drinksInformation.size() && !drinksInformation.get(drinksIndex).get(columnIdNumberDrinks).equals(name)) ++drinksIndex;
                if (drinksIndex == drinksInformation.size()) throw new Exception("Can't find item with id [" + id + "] in table drinks");
                sugar = drinksInformation.get(drinksIndex).get(findColumnInRow("sugar", drinksIndex, drinksInformation));
            } else
            if (productClass.equals("solid")) {
                while(solidsIndex < solidsInformation.size() && !solidsInformation.get(solidsIndex).get(columnIdNumberSolids).equals(name)) ++solidsIndex;
                if (solidsIndex == solidsInformation.size()) throw new Exception("Can't find item with id [" + id + "] in table solids");
                taste = solidsInformation.get(solidsIndex).get(findColumnInRow("taste", solidsIndex, solidsInformation));
            } else
            if (productClass.equals("species")) {
                while(solidsIndex < solidsInformation.size() && !solidsInformation.get(solidsIndex).get(columnIdNumberSolids).equals(name)) ++solidsIndex;
                if (solidsIndex == solidsInformation.size()) throw new Exception("Can't find item with id [" + id + "] in table solids");
                taste = solidsInformation.get(solidsIndex).get(findColumnInRow("taste", solidsIndex, solidsInformation));
            } else throw new Exception("Unknown type of product_class [" + productClass + "] from product [" + id + ", " + name + "]");
            */

            if (name == null || productType == null || productClass == null || calories == null || id == null)
                throw new Exception("Some of non-null by definition values are null: " + name + ", " + productType + ", " + calories + ", " + id);
            Product item;
            switch (Objects.requireNonNull(productClass)) {
                case "drinks":
                    item = new Drinks(name, Integer.parseInt(calories), Integer.parseInt(id), Boolean.parseBoolean(sugar));
                    break;
                case "solid":
                    if (taste != null)
                        item = new Solid(name, Integer.parseInt(calories), Integer.parseInt(id));
                    else
                        item = new Solid(name, Integer.parseInt(calories), Integer.parseInt(id));
                    break;
                case "Species":
                    System.out.println(taste);
                    item = new Species(name, Integer.parseInt(calories), Species.getEnumTaste(taste), Integer.parseInt(id));
                    break;
                case "product_class":
                    continue;
                default:
                    throw new Exception("Illegal type of product_class [" + productClass + "] of product [" + row + "]");
            }
            if (area != null) item.setArea(null); // change to reconvert array.ToString -> Array of Strings
            if (description != null) item.setDescription(description);
            result.add(item);
        }
        return result;
    }
}