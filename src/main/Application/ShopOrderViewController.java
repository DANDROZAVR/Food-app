package main.Application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Data.Parser;
import main.Model.Orders.shopOrder;
import main.Model.Products.Product;
import main.Model.Recipes.Recipe;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShopOrderViewController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Back;

    @FXML
    private VBox VBox;
    void setOrder(shopOrder o, ArrayList<Integer> prices) throws Exception {
        VBox.getChildren().clear();
        List<Hyperlink> links = new ArrayList<>();
        int cnt = 0;
        for(Integer ide: o.getContent()){
            if(ide%2==0) {
                Recipe res = Parser.parseRecipeById(ide);
                Hyperlink link = new Hyperlink(res.getName() + "  with price=" + prices.get(cnt));
                link.setTooltip(new Tooltip("weight: " + res.getWeight() + "\n" +
                        "All calories: " + res.getAllCalories() + "\n" + "Description: " + res.getDescription()));
                link.setOnAction(t -> {
                    FXMLLoader loader = LoadXML.load("ForOneRecipe.fxml");
                    try {
                        ((forOneRecipeController) loader.getController()).setRecipe(res);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    ((forOneRecipeController) loader.getController()).setSceneProduct(VBox.getScene());
                    Parent root = loader.getRoot();
                    ((Stage) link.getScene().getWindow()).setScene(new Scene(root));
                });
                links.add(link);
            }else{
                Product res = Parser.parseProductById(ide);
                Hyperlink link = new Hyperlink(res.getName() + "  with price=" + prices.get(cnt));
                link.setTooltip(new Tooltip("Product group: " + res.getType() + "\n" +
                        "Product class: " + res.getProductType()+ "\n" + "Calories " + res.getCalories()));
                link.setOnAction(t -> {
                    FXMLLoader loader = LoadXML.load("ForOneProductView.fxml");
                    ((ForOneProductViewController) loader.getController()).setProduct(res);
                    ((ForOneProductViewController) loader.getController()).setSceneProduct(VBox.getScene());
                    Parent root = loader.getRoot();
                    ((Stage) link.getScene().getWindow()).setScene(new Scene(root));
                });
                links.add(link);
            }
            ++cnt;
        }
        VBox.getChildren().addAll(links);
    }
    void setSceneBack(Scene scene){
        Back.setOnAction(t -> {
            ((Stage) Back.getScene().getWindow()).setScene(scene);
        });
    }
}
