// author: dandrozavr
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
}
