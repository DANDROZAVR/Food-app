package main.Data;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.ArrayList;

public class Query {
    public static ArrayList<ArrayList<String>> getFullInformation(String fromTable, int[] order) throws SQLException {
        StringBuilder query = new StringBuilder("SELECT * FROM " + fromTable);
        if (order.length > 0) {
            query.append("ORDER BY ");
            for (int i = 0; i < order.length; ++i) {
                if (i > 0)
                    query.append(",");
                query.append(order[i]);
            }
        }
        query.append(";");
        return Database.execute(query.toString());
    }
    public static ArrayList<ArrayList<String>> getFullInformation(String fromTable) throws SQLException {
        String query = new String("SELECT * FROM " + fromTable + ";");
        return Database.execute(query);
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
    public static int getNewIdFor(String S) throws SQLException {
        String query = new String("SELECT COUNT(*) FROM " + S + ";");
        int Id = 2 * (1 + Integer.parseInt(Database.execute(query).get(1).get(0)));
        if(S == "Recipes"){
            Id++;
        }
        return Id;
    }
    public static void addNewProduct(int Id, String product_type, String name, String description, String area, int calories) throws SQLException {
        String query = new String("INSERT INTO products(id_prod, product_type, name, description, area, calories) VALUES ("
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
        Database.execute(query);
    }
}
