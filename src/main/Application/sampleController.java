package main.Application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import main.Data.Parser;
import main.Data.Query;
import main.Model.Products.Product;
import main.Model.Products.Solid;

public class sampleController extends Main {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button show_all_products;

    @FXML
    private Button show_all_recipes;
    @FXML
    private Button goToHome;
    @FXML
    void openSettings(ActionEvent event) {
    }
    @FXML
    private Button setProductButton;
    @FXML
    private Button ButtonAddProducts;
    @FXML
    void initialize() {
        assert show_all_products != null : "fx:id=\"show_all_products\" was not injected: check your FXML file 'sample.fxml'.";
        show_all_products.setOnAction(event -> {
            FXMLLoader loader = LoadXML.load("ForProducts.fxml");
            Parent root = loader.getRoot();
            ((Stage) show_all_products.getScene().getWindow()).setScene(new Scene(root));
        });
        assert show_all_recipes != null : "fx:id=\"show_all_recipes\" was not injected: check your FXML file 'sample.fxml'.";
        show_all_recipes.setOnAction(event -> {
            FXMLLoader loader = LoadXML.load("ForRecipes.fxml");
            Parent root = loader.getRoot();
            ((Stage) show_all_recipes.getScene().getWindow()).setScene(new Scene(root));
        });
        setProductButton.setOnAction(event -> {
            try {
                //Product item = Parser.getProductsFrom(Query.getFullInformation("(select * from products left join drinks left join solids left join species)")).get(0);
                Product item = new Solid("apple", 31, 1, Solid.getEnumTaste("Sweat"));
                goToProduct(item);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        assert ButtonAddProducts != null : "fx:id=\"ButtonAddProducts\" was not injected: check your FXML file 'sample.fxml'.";
        ButtonAddProducts.setOnAction(event -> {
            FXMLLoader loader = LoadXML.load("ForAddingProducts.fxml");
            Parent root = loader.getRoot();
            ((Stage) ButtonAddProducts.getScene().getWindow()).setScene(new Scene(root));
        });
    }

    public void goToHome(ActionEvent actionEvent) {
        goToHome();
    }
}