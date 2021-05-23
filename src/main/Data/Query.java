package main.Data;

import main.Model.Products.Product;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.ArrayList;

public class Query {
    public static ArrayList<ArrayList<String>> getFullInformation(String fromTable, int[] order) throws SQLException {
        String query = new String("SELECT * FROM " + fromTable + orderQuery(order));
        return Database.execute(query);
    }
    public static String orderQuery(int[] order) {
        StringBuilder query = new StringBuilder();
        if (order.length > 0) {
            query.append("ORDER BY ");
            for (int i = 0; i < order.length; ++i) {
                if (i > 0)
                    query.append(",");
                query.append(order[i]);
            }
        }
        return query.toString();
    }
    public static ArrayList<ArrayList<String>> getFullInformation(String fromTable) throws SQLException {
        String query = new String("SELECT * FROM " + fromTable + ";");
        return Database.execute(query);
    }
    public static ArrayList<Product> getSimpleProductsWithoutSpecies() throws Exception {
        String query = new String("SELECT * FROM products;");
        return Parser.getProductsFrom(Database.execute(query));
    }
    public static ArrayList<Product> getSimpleProductsWithSpecies() throws Exception {
        String query = new String("SELECT * FROM products left join species_taste;");
        return Parser.getProductsFrom(Database.execute(query));
    }
    /*
    public static ArrayList<Product> getFullProductsWithoutSpecies() throws Exception {
        String query = new String("SELECT * FROM products;");
        return Parser.getProductsFrom(Database.execute(query));
    }
    public static ArrayList<Product> getFullProductsWithSpecies() throws Exception {
        String query = new String("SELECT * FROM products left join species_taste;");
        return Parser.getProductsFrom(Database.execute(query));
    }
    change query
     */
    public static int getCaloriesFromProducts(String fromTable, int item) throws SQLException {
        String query = "SELECT calories FROM " + fromTable + " WHERE id_prod=" + String.valueOf(item) + ";";
        return Integer.parseInt(Database.execute(query).get(1).get(0));
    }
    public static ArrayList<ArrayList<String>> getByName(String fromTable, String name) throws SQLException {
        String query = new String("SELECT * FROM " + fromTable + " where name = '" + name + "';");
        return Database.execute(query);
    }
    public static ArrayList<ArrayList<String>> getByNamePrefix(String fromTable, String prefixName) throws SQLException {
        String query = new String("SELECT * FROM " + fromTable + " where name like '" + prefixName + "%';");
        return Database.execute(query);
    }
    public static int getNewIdFor(String S) throws SQLException {
        String query = new String("SELECT COUNT(*) FROM " + S + ";");
        int Id = 2 * (1 + Integer.parseInt(Database.execute(query).get(1).get(0)));
        if(S == "Recipes"){
            Id++;
        }
        return Id;
    }
    public static boolean addNewProduct(int Id, String product_type, String name, String description, String area, int calories) throws SQLException {
        if(Integer.parseInt(Database.execute(new String("select count(*) from products where id_prod = " + Id + ";")).get(1).get(0)) != 0){
            return false;
        }
        if(Integer.parseInt(Database.execute(new String("select count(*) from products where name = '" + name + "';")).get(1).get(0)) != 0){
            return false;
        }
        String query = new String("INSERT INTO products(id_prod, product_class, name, description, area, calories) VALUES ("
                        + Id
                        + ", '"
                        + product_type
                        +"', '"
                        + name
                        +"', '"
                        +description
                        +"', '"
                        +area
                        +"', "
                        +calories
                        +");");
        try{
            Database.execute(query);
        }catch (Exception e){

        }
        query = new String("select count(*) from products where " +
                "id_prod = " + Id + " AND " +
                "product_type = '" + product_type + "' AND " +
                "name = '" + name + "' AND " +
                "description = '" + description + "' AND " +
                "area = '" + area + "' AND " +
                "calories = " + calories + ";"
        );
        if(Integer.parseInt(Database.execute(query).get(1).get(0)) == 0){
            return false;
        }
        return true;

    }
}
