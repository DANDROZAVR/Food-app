package main.Helpers.CSVReader;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class ParseProductsInput {
    public static void parse() {
        try {
            String project_path = new File(".").getCanonicalPath();
            String file_path = "/src/resources/CSV/food2.csv";
            ArrayList<String[]> table = CSVLoad.load(project_path + file_path);
            assert table != null;
            //change sugar
            System.out.println(Arrays.toString(table.get(0)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*public static void main(String[] args) {
        parse();
    }*/
}
