package main.Application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }
    public static void main(String[] args) {
        try {
            main.Data.Database.connect("jdbc:postgresql://localhost:5432/test", "test", "testpass");
            //int empty[] = {};
            //System.out.println(main.Data.Query.getFullInformation("Products", empty));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error set connection to the database");
            System.exit(1);
        }
        //launch(args);
    }
}
