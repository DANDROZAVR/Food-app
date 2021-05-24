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
    public static ArrayList<ArrayList<String>> getByNamePrefix_withSpecies(String fromTable, String prefixName) throws SQLException {
        String query = new String("SELECT * from (SELECT * FROM " + fromTable + " where name like '" + prefixName + "%') as products1 left join species_taste using(id_prod);");
        System.out.println(query);
        return Database.execute(query);
    }
    public static ArrayList<ArrayList<String>> getByNamePrefix_withDrinks(String fromTable, String prefixName) throws SQLException {
        String query = new String("SELECT * from (SELECT * FROM " + fromTable + " where name like '" + prefixName + "%') as products1 left join drinks_info using(id_prod);");
        System.out.println(query);
        return Database.execute(query);
    }
    public static ArrayList<ArrayList<String>> getByNamePrefix_all(String fromTable, String prefixName) throws SQLException {
        String query = new String("SELECT * from (" +
                                            "SELECT * FROM " + fromTable + " where name like '" + prefixName + "%'" +
                                        ") as products1 left join drinks_info using(id_prod)" +
                                        " left join species_taste using(id_prod);");
        System.out.println(query);
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
    public static boolean addNewProduct(int Id, String product_group, String product_class, String name, String description, String area, int calories) throws SQLException {
        if(Integer.parseInt(Database.execute(new String("select count(*) from products where id_prod = " + Id + ";")).get(1).get(0)) != 0){
            return false;
        }
        if(Integer.parseInt(Database.execute(new String("select count(*) from products where name = '" + name + "';")).get(1).get(0)) != 0){
            return false;
        }
        String query = new String("INSERT INTO products(id_prod, product_group, product_class, name, description, calories) VALUES ("
                + Id
                + ", '"
                + product_group
                +"', '"
                + product_class
                +"', '"
                + name
                +"', '"
                +description
                +"', "
                +calories
                +");");
        try{
            Database.update(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        query = new String("select count(*) from products where " +
                "id_prod = " + Id + " AND " +
                "product_group = '" + product_group + "' AND " +
                "product_class = '" + product_class + "' AND " +
                "name = '" + name + "' AND " +
                "description = '" + description + "' AND " +
                "calories = " + calories + ";"
        );
        if(Integer.parseInt(Database.execute(query).get(1).get(0)) == 0){
            return false;
        }
        query = new String("INSERT INTO products_areatag(id, area) VALUES ("
                + Id
                + ", '"
                + area
                + "');"
        );
        Database.update(query);
        return true;

    }
}
