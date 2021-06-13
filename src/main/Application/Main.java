package main.Application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Data.Parser;
import main.Helpers.CSVReader.ParseProductsInput;
import main.Helpers.CSVReader.ParserRecipesInput;
import main.Model.Products.Product;
import org.postgresql.replication.fluent.AbstractStreamBuilder;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.StreamSupport;

public class Main extends Application {
    public static Stage primaryStage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("ForSearch.fxml"));
        primaryStage.setTitle("Food-app");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }
    public static void main(String[] args) {
        try {
            main.Data.Database.connect("jdbc:postgresql://localhost:5432/test", "test", "testpass");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error set connection to the database");
            System.exit(1);
        }
        //ParseProductsInput.parse();
        //ParserRecipesInput.parse();
        //System.exit(0);
        launch(args);
    }
    public static void setScene(FXMLLoader loader) {
        Parent root = loader.getRoot();
        ((Stage) primaryStage.getScene().getWindow()).setScene(new Scene(root));
    }
    public static void goToHome() {
        FXMLLoader loader = LoadXML.load("sample.fxml");
        setScene(loader);
    }
    public static void goToProducts() {
        FXMLLoader loader = LoadXML.load("ForProducts.fxml");
        setScene(loader);
    }
    public static void goToRecipes() {
        FXMLLoader loader = LoadXML.load("ForRecipes.fxml");
        setScene(loader);
    }
    public static void goToProduct(Product item) {
        FXMLLoader loader = LoadXML.load("ForOneProductView.fxml");
        setScene(loader);
        ((ForOneProductViewController) loader.getController()).setProduct(item);
    }
}
