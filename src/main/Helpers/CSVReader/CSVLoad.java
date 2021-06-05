package main.Helpers.CSVReader;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Locale;


public class CSVLoad {
    public static ArrayList<String []> load(String filePath) {

        ArrayList<String[]> res = new ArrayList<>();
        try {
            Locale.setDefault(Locale.UK);
            CSVReader reader = new CSVReader(new FileReader(filePath));
            String[] lineInArray;
            while ((lineInArray = reader.readNext()) != null) {
                res.add(lineInArray);
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return res;
    }
}
