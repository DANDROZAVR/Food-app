package main.Helpers.CSVReader;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;


public class CSVLoad {
    public static ArrayList<String> parseString(String queue) {
        int quotes = 0;
        boolean increase = false;
        StringBuilder actual = new StringBuilder();
        ArrayList<String> res = new ArrayList<>();
        for (int i = 0; i < queue.length(); ++i) {
            if (queue.charAt(i) == '\'') continue;
            if (queue.charAt(i) == ';') {
                res.add(actual.toString());
                break;
            }
            if (queue.charAt(i) == '"') {
                if (quotes == 2 || quotes == 0) {
                    increase = !increase;
                }
                if (increase) ++quotes; else --quotes;
                continue;
            }
            if (quotes == 0 || (res.size() == 0 && queue.charAt(i) == ',')) {
                res.add(actual.toString());
                quotes = 0;
                increase = false;
                actual.setLength(0);
                continue;
            }
            actual.append(queue.charAt(i));
        }
        return res;
    }
    public static ArrayList<ArrayList<String>> load(String filePath) {
        ArrayList<ArrayList<String>> res = new ArrayList<>();
        try {
            StringBuilder resultStringBuilder = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String lineInput;
                while ((lineInput = br.readLine()) != null) {
                    ArrayList<String> line = parseString(lineInput);
                    if (line.size() == 48)
                        res.add(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return res;
    }
}
