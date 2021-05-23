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
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Data.Parser;
import main.Model.Products.Product;

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
    void setSceneProduct(Scene sceneProduct){
        this.sceneProduct = sceneProduct;
    }
    void setProduct(Product item) {
        ToProducts.setOnAction(t -> {
            ((Stage) product_area.getScene().getWindow()).setScene(sceneProduct);
        });
        //((TextArea)((HBox)VBoxProduct.getChildren().get(0)).getChildren().get(0)).setAccessibleText(item.getName());
        product_name.setText(item.getName());
        product_area.setText(Arrays.toString(item.getArea()));
        product_type.setText(item.getProductType());
        product_calories.setText(String.valueOf(item.getCalories()));
        product_description.setText(item.getDescription());
        if (item.getLinks() != null) {
            StringBuilder linksString = new StringBuilder();
            for (int i = 0; i < item.getLinks().length; ++i) {
                if (i > 0) linksString.append('\n');
                linksString.append(item.getLinks()[i]);
            }
            product_links.setText(linksString.toString());
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

    }
}
