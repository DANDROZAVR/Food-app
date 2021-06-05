package main.Helpers.IconFinder.CSVReader;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.util.ArrayList;

public class CSVLoad {
    public static ArrayList<String []> load(String filePath) {
        ArrayList<String[]> res = new ArrayList<>();
        try {
            CSVReader reader = new CSVReader(new FileReader(filePath));
            String[] lineInArray;
            while ((lineInArray = reader.readNext()) != null) {
                res.add(lineInArray);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return res;
    }

}
