package main.Controller;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class LoadXML {
    public static FXMLLoader load(String path) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(LoadXML.class.getResource(path));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loader;
    }
}
