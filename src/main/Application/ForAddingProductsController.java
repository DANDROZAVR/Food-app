package main.Application;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.Data.Query;
import main.Model.Products.Product;

public class ForAddingProductsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField GetName;

    @FXML
    private TextField GetProductGroup;

    @FXML
    private ChoiceBox<String> GetProductClass;

    @FXML
    private TextField GetDescription;

    @FXML
    private TextField GetArea;

    @FXML
    private TextField GetCalories;

    @FXML
    private Button AddProduct;
    @FXML
    public void goToHome(ActionEvent e) { Main.goToHome(); }
    @FXML
    private Button cancelButton;
    @FXML
    private Label error_out;

    @FXML
    private void start_GetProductClass(){
        ObservableList<String> GetProductClassList = FXCollections.observableArrayList("Species", "Solids", "Drinks");
        GetProductClass.setValue("Species");
        GetProductClass.setItems(GetProductClassList);
    }

    @FXML
    void initialize(){
        start_GetProductClass();
        AddProduct.setOnAction(event -> {
            int Id = 0;
            try{
                Id = Query.getNewIdFor("products");
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                String product_group = GetProductGroup.getText(), product_class = GetProductClass.getValue(), name = GetName.getText(), description = GetDescription.getText(), area = GetArea.getText();
                int calories = Integer.parseInt(GetCalories.getText());
                if(!Product.checkParameters(product_group, product_class, name, description, area, calories)){
                    error_out.setTextFill(Color.web("#dd0e0e", 0.8));
                    error_out.setText("ERROR");
                    return;
                }
                boolean _try = Query.addNewProduct(Id, product_group, product_class, name, description, area, calories);
                if(_try){
                    error_out.setTextFill(Color.web("#16b221", 0.8));
                    error_out.setText("OK");
                }else{
                    error_out.setTextFill(Color.web("#dd0e0e", 0.8));
                    error_out.setText("ERROR");
                }
            }catch(Exception e){
                e.printStackTrace();
                error_out.setTextFill(Color.web("#dd0e0e", 0.8));
                error_out.setText("ERROR");
            }
        });
    }
}
