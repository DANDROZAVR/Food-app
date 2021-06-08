package main.Helpers.Generators;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class RandomLoad {
    private Random rand = null;
    RandomLoad() { rand = new Random(); }
    public ArrayList<String> load(String filePath) {
        ArrayList<String> res = new ArrayList<String>();
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String lineInput;
            while ((lineInput = br.readLine()) != null) {
                res.add(lineInput);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
    public ArrayList<String> names;
    public void names(String filePath) { names = load(filePath); }
    public String getName() {
        return names.get(rand.nextInt(names.size() - 1));
    }
    public String getDescription() {
        return null;
    }

}
