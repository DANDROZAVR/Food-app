// author: dandrozavr
package main.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static Connection db = null;
    public static void connect(String serverAddres, String user, String password) throws Exception {
        try {
            db = DriverManager.getConnection(serverAddres, user, password);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error connecting to the database: " + e.getClass().getName() + e.getMessage());
            throw new Exception(e);
        }
        // Set connection
    }
    public static void disconnect() {
        try {
            db.close();
            db = null;
        } catch (Exception e) {
            e.printStackTrace();;
            System.err.println("Error disconnecting to the database: " + e.getClass().getName() + e.getMessage());
        }
        // Closes connection
    }
    public static ArrayList<ArrayList<String>> execute(String query) throws SQLException {
        Statement queryStatement = null;
        try {
            ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
            queryStatement = db.createStatement();
            ResultSet queryResult = queryStatement.executeQuery(query);
            ResultSetMetaData queryMeta = queryResult.getMetaData();
            result.add(new ArrayList<String>());
            int columnsNumber = queryMeta.getColumnCount();
            for (int i = 1; i <= columnsNumber; ++i)
                result.get(0).add(queryMeta.getColumnName(i));
            int rows = 1;
            while(queryResult.next()) {
                result.add(new ArrayList<String>());
                for (int i = 1; i <= columnsNumber; ++i)
                    result.get(rows).add(queryResult.getString(i));
                ++rows;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();;
            System.err.println("Error executing query [" + query + "]: " + e.getClass().getName() + e.getMessage());
            throw e;
        } finally {
            if (queryStatement != null) {
                try {
                    queryStatement.close();
                } catch (Exception e) {
                    e.printStackTrace();;
                    System.err.println("Error closing query [" + query + "]: " + e.getClass().getName() + e.getMessage());
                }
            }
        }
        //Returns the result of the query, with the first ling contains column names
    }
    public static int update(String updateQuery) throws SQLException {
        Statement updateStamenet = null;
        try {
            updateStamenet = db.createStatement();
            return updateStamenet.executeUpdate(updateQuery);
        } finally {
            updateStamenet.close();
        }
    }
}
