package main.Data;

import main.Model.Products.Drinks;
import main.Model.Products.Product;
import main.Model.Products.Species;

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
    Tags, Minerals, Vitamins
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
    public static ArrayList<ArrayList<String>> getByNamePrefix_all(String fromTable, String prefixName) throws SQLException {
        String query = new String(
                "SELECT * FROM(" +
                        "SELECT * from (SELECT * FROM " + fromTable + " where name like '" + prefixName + "%') as products1 left join species_taste using(id)"
                        + ") as products2 left join products_areatag using(id);");
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
    public static void addNewProduct(Product p) throws SQLException, ClassNotFoundException, Exception {
        if(Integer.parseInt(Database.execute(new String("select count(*) from products where id = " + p.getId() + ";")).get(1).get(0)) != 0){
            throw new Exception("has this id");
        }
        if(Integer.parseInt(Database.execute(new String("select count(*) from products where name = '" + p.getName() + "';")).get(1).get(0)) != 0){
            throw new Exception("has this name");
        }
        String query = new String("INSERT INTO products(id, product_group, product_class, name, description, calories) VALUES ("
                + p.getId()
                + ", '"
                + p.getProductType()
                +"', '"
                + Parser.getProductClass(p.getClass())
                +"', '"
                + p.getName()
                +"', '"
                +p.getDescription()
                +"', "
                +p.getCalories()
                +");");
        try{
            Database.update(query);
        }catch (Exception e){
            e.printStackTrace();
        }

        query = new String("select count(*) from products where " +
                "id = " + p.getId() + " AND " +
                "product_group = '" + p.getProductType() + "' AND " +
                "product_class = '" + Parser.getProductClass(p.getClass())+ "' AND " +
                "name = '" + p.getName() + "' AND " +
                "description = '" + p.getDescription() + "' AND " +
                "calories = " + p.getCalories() + ";"
        );
        if(Integer.parseInt(Database.execute(query).get(1).get(0)) == 0){
            throw new Exception("can't add to products");
        }
        if(Parser.getProductClass(p.getClass()).equals("Solid")){

        }
        if(Parser.getProductClass(p.getClass()).equals("Species")){
            try{
                query = new String("INSERT INTO species_taste(id, taste) VALUES("
                +p.getId()
                +", '"
                +((Species)p).getTaste()
                +"');"
                );
                Database.update(query);
            } catch (Exception e){
                e.printStackTrace();
                query = new String("delete from products where id = " + p.getId() + ";");
                Database.update(query);
                throw new Exception("can't add to Species");
            }
        }
        if(Parser.getProductClass(p.getClass()).equals("Drinks")){
            query = new String("delete from products where id = " + p.getId() + ";");
            Database.update(query);
            throw new Exception("can't add to Drinks");
            /*
            try{
                query = new String("INSERT INTO drinks_taste(id, sugar, colour) VALUES("
                        +p.getId()
                        +", "
                        +((Drinks)p)
                        +");"
                );
                Database.update(query);
            } catch (Exception e){
                e.printStackTrace();
                query = new String("delete * from products where id = " + p.getId() + ";");
                Database.update(query);
                throw new Exception("can't add to Drinks");
            }

             */
        }
        /*
        query = new String("INSERT INTO products_areatag(id, area) VALUES ("
                + Id
                + ", '"
                + area
                + "');"
        );
        Database.update(query);
        */
    }
}
