// author: dandrozavr
package main.Data;

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
}
