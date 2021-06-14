package main.Application;


import main.Helpers.IconFinder.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jdk.jfr.Event;
import main.Data.Parser;
import main.Data.Query;

import javax.swing.*;

public class ForProductsController extends Main {
    final ListView listView = new ListView();
    @FXML
    private ResourceBundle resources;

    @FXML
    private VBox VBox;

    @FXML
    private URL location;

    @FXML
    private Button cancelButton;
    @FXML
    public void goToHome(ActionEvent e) {
        goToHome();
    }
    @FXML
    private TextField GetText;
    @FXML
    private Button ButtonFind;
    @FXML
    private Button caloriesDesc;
    @FXML
    private Button caloriesAsc;
    @FXML
    List<Hyperlink> links;
    @FXML
    private Button idAsc;
    @FXML
    private Button idDesc;

    public void setProducts(String table) throws SQLException {
        ArrayList<ArrayList<String>> output = Query.getByNamePrefix_all(table,"");
        setProductFromResult(output);
    }
    public void setProductFromResult(ArrayList<ArrayList<String>> output) {
        links = new ArrayList<>();
        VBox.getChildren().clear();
        listView.getItems().clear();
        boolean byl = false;
        HashSet<String> hs = new HashSet<>();
        for (ArrayList<String> s : output) {
            if (s.get(4) != null && !s.get(4).equals("description")) {
                Hyperlink link = new Hyperlink(s.get(3));
                /*Thread t2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FindIcon.loadIconFromNet(s.get(3));
                    }
                });*/
                /*if (!hs.contains(s.get(3))) {
                    FindIcon.loadIconFromNet(s.get(3));
                    System.out.println(s.get(3));
                    hs.add(s.get(3));
                }*/
                //byl = true;
                //if (byl) break;
                link.setTooltip(new Tooltip("Product group: " + s.get(1) + "\n" +
                        "Product class: " + s.get(2)+ "\n" + "Calories " + s.get(5)));
                link.setOnAction(t1 -> {
                    FXMLLoader loader = LoadXML.load("ForOneProductView.fxml");
                    ArrayList<ArrayList<String>> temp = new ArrayList<>();
                    temp.add(output.get(0));
                    temp.add(s);
                    try {
                        ((ForOneProductViewController) loader.getController()).setProduct(Parser.getProductsFrom(temp).get(0));
                        ((ForOneProductViewController) loader.getController()).setSceneProduct(GetText.getScene());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Parent root = loader.getRoot();
                    ((Stage) GetText.getScene().getWindow()).setScene(new Scene(root));
                });
                links.add(link);
            }
        }
        listView.getItems().addAll(links);
        VBox.getChildren().addAll(listView);
    }
    @FXML
    public void openSettings(javafx.event.ActionEvent actionEvent) {
    }
    @FXML
    private Button settings;
    @FXML
    private Hyperlink goToSearch;
    public void butFind() {
        links.clear();
        VBox.getChildren().clear();
        listView.getItems().clear();
        try {
            ArrayList<ArrayList<String>> output = Query.getByNamePrefix_all("products", GetText.getText());
            for (ArrayList<String> s : output) {
                if (!s.get(4).equals("description")) {
                    Hyperlink link = new Hyperlink(s.get(3));
                    link.setTooltip(new Tooltip("Product group: " + s.get(1) + "\n" +
                            "Product class: " + s.get(2)+ "\n" + "Calories " + s.get(5)));
                    link.setOnAction(t -> {
                        FXMLLoader loader = LoadXML.load("ForOneProductView.fxml");
                        ArrayList<ArrayList<String>> temp = new ArrayList<>();
                        temp.add(output.get(0));
                        temp.add(s);
                        try {
                            ((ForOneProductViewController) loader.getController()).setProduct(Parser.getProductsFrom(temp).get(0));
                            ((ForOneProductViewController) loader.getController()).setSceneProduct(GetText.getScene());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Parent root = loader.getRoot();
                        ((Stage) GetText.getScene().getWindow()).setScene(new Scene(root));
                    });
                    links.add(link);
                }
            }
            listView.getItems().addAll(links);
            VBox.getChildren().addAll(listView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void initialize() {
        try {
            //setProducts("products");
        } catch (Exception e) {
            e.printStackTrace();
        }
        caloriesAsc.setOnAction(event -> {
            try {
                setProducts("(select * from products order by calories) a");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        caloriesDesc.setOnAction(event -> {
            try {
                setProducts("(select * from products order by calories desc) a");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        idDesc.setOnAction(event -> {
            try {
                setProducts("(select * from products order by id_prod desc) a");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        idAsc.setOnAction(event -> {
            try {
                setProducts("(select * from products order by id_prod) a");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        goToSearch.setOnAction(event -> {
            FXMLLoader loader = LoadXML.load("ForSearch.fxml");
            Parent root = loader.getRoot();
            ((Stage) VBox.getScene().getWindow()).setScene(new Scene(root));
        });
        ButtonFind.setOnAction(event -> {
          butFind();
        });
    }


}