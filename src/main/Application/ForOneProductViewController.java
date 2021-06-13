package main.Application;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Data.Query;
import main.Model.Products.Product;
import main.Model.Products.Solids;

public class ForOneProductViewController extends Main {
    Scene sceneProduct;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private VBox VBoxProduct;
    @FXML
    private TextArea product_name;
    @FXML
    private ImageView product_icon;
    @FXML
    private TextArea product_type;
    @FXML
    private TextArea product_calories;
    @FXML
    private TextArea product_description;
    @FXML
    private TextArea product_area;
    @FXML
    private TextArea product_links;
    @FXML
    private Button ToProducts;
    @FXML
    private Label fat;
    @FXML
    private Label saturated_fat;
    @FXML
    private Label protein;
    @FXML
    private Label carbo;
    @FXML
    private Label sugar_total;
    @FXML
    private Label zinc;
    @FXML
    private Label iron;
    @FXML
    private Label calcium;
    @FXML
    private Label magnesium;
    @FXML
    private Label vitamin_A;
    @FXML
    private Label vitamin_B6;
    @FXML
    private Label vitamin_B12;
    @FXML
    private Label vitamin_C;
    @FXML
    private Label vitamin_E;
    @FXML
    private Label vitamin_K;
    @FXML
    private TextArea Tags;
    void setSceneProduct(Scene sceneProduct){
        this.sceneProduct = sceneProduct;
    }
    @FXML
    void setProduct(Product item) {
        //((TextArea)((HBox)VBoxProduct.getChildren().get(0)).getChildren().get(0)).setAccessibleText(item.getName());
        product_name.setText(item.getName());
        product_type.setText(item.getType());
        product_calories.setText(String.valueOf(item.getCalories()));
        product_description.setText(item.repair(item.getDescription()));

       if (item instanceof Solids) {
           Solids sld = (Solids) item;
           if (sld.nutrient != null) {
               fat.setText(String.valueOf(sld.nutrient.getFat()));
               saturated_fat.setText(String.valueOf(sld.nutrient.getsaturated_fat()));
               protein.setText(String.valueOf(sld.nutrient.getProtein()));
               carbo.setText(String.valueOf(sld.nutrient.getCarbo()));
               sugar_total.setText(String.valueOf(sld.nutrient.getSugar()));

               zinc.setText(String.valueOf(sld.nutrient.getZinc()));
               iron.setText(String.valueOf(sld.nutrient.getIron()));
               calcium.setText(String.valueOf(sld.nutrient.getCalcium()));
               magnesium.setText(String.valueOf(sld.nutrient.getMagnesium()));
           }
           if (sld.vitamins != null) {
               vitamin_A.setText(String.valueOf(sld.vitamins.a));
               vitamin_B6.setText(String.valueOf(sld.vitamins.b6));
               vitamin_B12.setText(String.valueOf(sld.vitamins.b12));
               vitamin_C.setText(String.valueOf(sld.vitamins.c));
               vitamin_E.setText(String.valueOf(sld.vitamins.e));
               vitamin_K.setText(String.valueOf(sld.vitamins.k));
           }

       }
        if (item.getLinks() != null) {
            StringBuilder linksString = new StringBuilder();
            for (int i = 0; i < item.getLinks().length; ++i) {
                if (i > 0) linksString.append('\n');
                linksString.append(item.getLinks()[i]);
            }
            product_links.setText(linksString.toString());
        }
        try {
            Tags.setText(Query.getTagsById(item.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Image nImage = null;
        try {
             nImage = new Image("@../../resources/Icons/" + item.getName() + ".png");
        } catch (IllegalArgumentException ignored) {}
        product_icon.setImage(nImage);
    }


    @FXML
    void initialize() {
        assert product_name != null : "fx:id=\"product_name\" was not injected: check your FXML file 'ForProductIcon.fxml'.";
        ToProducts.setOnAction(t -> {
            ((Stage) product_name.getScene().getWindow()).setScene(sceneProduct);
        });
    }
}
