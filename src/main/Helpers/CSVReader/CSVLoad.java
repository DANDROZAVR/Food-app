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
        int max_val = 1;
        char last;
        char now = '~';
        for (int i = 0; i < queue.length(); ++i) {
            last = now;
            now = queue.charAt(i);
            if (now == '\'') now = '`';
            if (now == ';') {
                res.add(actual.toString());
                break;
            }
            if (now == '"') {
                if (last == now && max_val == 1)
                    max_val = 2;
                if (quotes == max_val || quotes == 0) {
                    increase = !increase;
                }
                if (increase) ++quotes; else --quotes;
                continue;
            }
            if ((quotes == 0 && now == ',') || (res.size() == 0 && now == ',')) {
                res.add(actual.toString());
                quotes = 0;
                increase = false;
                actual.setLength(0);
                continue;
            }
            actual.append(now);
        }
        if (actual.length() > 0)
            res.add(actual.toString());
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
                    if (!(res.size() > 0 && line.size() != res.get(0).size())) res.add(line);
                    //if (res.size() > 15) break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return res;
    }
}
