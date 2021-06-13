package main.Application;

import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Data.Database;
import main.Data.Parser;
import main.Model.Orders.shopOrder;
import main.Model.Products.Product;
import main.Model.Recipes.Recipe;
import main.Model.Shops.Shop;

public class ShopController {
    Map<Hyperlink, Integer> helper;
    private final ListView listView = new ListView();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Back;

    @FXML
    private Label Content;

    @FXML
    private VBox Vbox;

    @FXML
    private Button order;
    @FXML
    private VBox VBoxOrder;
    @FXML
    private Button history;
    void setShop(Shop shop)  throws Exception {
        helper = new HashMap<>();
        ArrayList<Integer> menu = shop.getMenu();
        List<Hyperlink> links = new ArrayList<>();
        links.clear();
        Vbox.getChildren().clear();
        listView.getItems().clear();
        for (Integer ide : menu) {
            if(ide%2==0) {
                Recipe res = Parser.parseRecipeById(ide);
                Hyperlink link = new Hyperlink(res.getName());
                helper.put(link, ide);
                link.setTooltip(new Tooltip("weight: " + res.getWeight() + "\n" +
                        "All calories: " + res.getAllCalories() + "\n" + "Description: " + res.getDescription()));
                link.setOnAction(t -> {
                    FXMLLoader loader = LoadXML.load("ForOneRecipe.fxml");
                    try {
                        ((forOneRecipeController) loader.getController()).setRecipe(res);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    ((forOneRecipeController) loader.getController()).setSceneProduct(Vbox.getScene());
                    Parent root = loader.getRoot();
                    ((Stage) link.getScene().getWindow()).setScene(new Scene(root));
                });
                links.add(link);
            }else{
                Product res = Parser.parseProductById(ide);
                Hyperlink link = new Hyperlink(res.getName());
                helper.put(link, ide);
                link.setTooltip(new Tooltip("Product group: " + res.getType() + "\n" +
                        "Product class: " + res.getProductType()+ "\n" + "Calories " + res.getCalories()));
                link.setOnAction(t -> {
                    FXMLLoader loader = LoadXML.load("ForOneProductView.fxml");
                    ((ForOneProductViewController) loader.getController()).setProduct(res);
                    ((ForOneProductViewController) loader.getController()).setSceneProduct(Vbox.getScene());
                    Parent root = loader.getRoot();
                    ((Stage) link.getScene().getWindow()).setScene(new Scene(root));
                });
                links.add(link);
            }
        }
        ArrayList<Integer> order1 = new ArrayList<>();
        for(Hyperlink l: links){
            HBox temp1 = new HBox();
            Button add = new Button("add");
            temp1.getChildren().addAll(l,add);
            Vbox.getChildren().addAll(temp1);
            add.setOnAction(t1 -> {
                Vbox.getChildren().remove(temp1);
                HBox temp2 = new HBox();
                Button delete = new Button("delete");
                temp2.getChildren().addAll(l,delete);
                order1.add(helper.get(l));
                VBoxOrder.getChildren().add(temp2);
                delete.setOnAction(t2 -> {
                    order1.remove(helper.get(l));
                    temp1.getChildren().remove(add);
                    temp1.getChildren().addAll(l,add);
                    VBoxOrder.getChildren().remove(temp2);
                    Vbox.getChildren().add(temp1);
                });
            });
        }
        order.setOnAction(t -> {
            System.out.println(order1);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            for(Integer r: order1){
                ArrayList<ArrayList<String>> idOr = new ArrayList<>();
                try {
                    idOr = Database.execute("select nextval('for_id_shopOrders');");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                ArrayList<ArrayList<String>> temp = new ArrayList<>();
                if(r%2 == 0){
                    try {
                        temp = Database.execute("select price from shops_content_recipes where id_rec = " + r + ";");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }else{
                    try {
                        temp = Database.execute("select price from shops_content_products where id_prod = " + r + ";");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                try {
                    Database.update("insert into shopOrders values ("
                            + idOr.get(1).get(0)
                            +','
                            + shop.getId()
                            + ','
                            + r
                            +","
                            + temp.get(1).get(0)
                            +",'"
                            +  dateFormat.format(date)
                            +"');");
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            try {
                setShop(shop);
            } catch (Exception e) {
                e.printStackTrace();
            }
            VBoxOrder.getChildren().clear();
            order1.clear();
        });
        history.setOnAction(t -> {
            FXMLLoader loader = LoadXML.load("historyShop.fxml");
            ArrayList<shopOrder> hist = new ArrayList<>();
            try {
                hist = shop.getOrders(shop.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(!hist.isEmpty()){
                ((HistoryShopController) loader.getController()).setShop(hist);
                ((HistoryShopController) loader.getController()).setSceneBack(Vbox.getScene());
                Parent root = loader.getRoot();
                ((Stage) Vbox.getScene().getWindow()).setScene(new Scene(root));
            }else{
                System.out.println("Empty");
            }
        });
    }
    void setSceneBack(Scene scene){
        Back.setOnAction(t -> {
            ((Stage) Back.getScene().getWindow()).setScene(scene);
        });
    }

    @FXML
    void initialize() { }
}