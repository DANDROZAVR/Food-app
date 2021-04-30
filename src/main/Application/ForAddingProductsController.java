package main.Application;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    private TextField GetProductType;

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
    void initialize(){
        AddProduct.setOnAction(event -> {
            int Id = 0;
            try{
                Id = Query.getNewIdFor("products");
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                Query.addNewProduct(Id, GetProductType.getText(), GetName.getText(), GetDescription.getText(), GetArea.getText(), Integer.parseInt(GetCalories.getText()));
            }catch(Exception e){
                e.printStackTrace();
            }
        });
    }
}
