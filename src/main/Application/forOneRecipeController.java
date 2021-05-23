package main.Application;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import main.Model.Products.Product;
import main.Model.Recipes.Recipe;

public class forOneRecipeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea Name;

    @FXML
    private TextArea weight;

    @FXML
    private TextArea calories;

    @FXML
    private TextArea description;

    @FXML
    private TextArea links;

    /*void setRecipe(Recipe item) {
        //((TextArea)((HBox)VBoxProduct.getChildren().get(0)).getChildren().get(0)).setAccessibleText(item.getName());
        Name.setText(item.getName());
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
    }*/
    @FXML
    void initialize() {


    }
}