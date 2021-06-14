package main.Application;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import main.Data.Parser;
import main.Data.Query;
import javafx.scene.layout.VBox;


public class ForRecipesController extends Main {
    private final ListView listView = new ListView();
    @FXML
    private VBox VBox;
    @FXML
    private Button ButtonFind;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TextArea textArea;
    @FXML
    private Button cancelButton;
    @FXML
    public void goToHome(ActionEvent e) { goToHome();}
    @FXML
    void openSettings(ActionEvent event) {
    }
    @FXML
    private TextField GetText;
    @FXML
    private Button caloriesDesc;
    @FXML
    private Button caloriesAsc;
    @FXML
    List<Hyperlink> links;
    @FXML
    void setRecipes(String table) throws SQLException {
        links = new ArrayList<>();
        VBox.getChildren().clear();
        listView.getItems().clear();
        ArrayList<ArrayList<String>> output = Query.getFullInformation(table);
        for (ArrayList<String> s : output) {
            if (!s.get(4).equals("description")) {
                Hyperlink link = new Hyperlink(s.get(1));
                link.setTooltip(new Tooltip(
                        "Calories per 100 g: " + s.get(3)+ "\n" + "Description: " + s.get(4)));
                link.setOnAction(t -> {
                    FXMLLoader loader = LoadXML.load("forOneRecipe.fxml");
                    ArrayList<ArrayList<String>> temp = new ArrayList<>();
                    temp.add(output.get(0));
                    temp.add(s);
                    try {
                        ArrayList<Pair<Integer,Integer>> content = Query.getAllContentOfRecipe(Integer.parseInt(s.get(0)));
                        ((forOneRecipeController) loader.getController()).setRecipe(Parser.getRecipesFrom(temp, content).get(0));
                        ((forOneRecipeController) loader.getController()).setSceneProduct(GetText.getScene());
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
    public void setRecipesFromResult(ArrayList<ArrayList<String>> output) {
        links = new ArrayList<>();
        VBox.getChildren().clear();
        listView.getItems().clear();
        for (ArrayList<String> s : output) {
            if (!s.get(4).equals("description")) {
                Hyperlink link = new Hyperlink(s.get(1));
                link.setTooltip(new Tooltip(
                        "Calories per 100 g: " + s.get(3)+ "\n" + "Description: " + s.get(4)));
                link.setOnAction(t -> {
                    FXMLLoader loader = LoadXML.load("forOneRecipe.fxml");
                    ArrayList<ArrayList<String>> temp = new ArrayList<>();
                    temp.add(output.get(0));
                    temp.add(s);
                    try {
                        ArrayList<Pair<Integer,Integer>> content = Query.getAllContentOfRecipe(Integer.parseInt(s.get(0)));
                        ((forOneRecipeController) loader.getController()).setRecipe(Parser.getRecipesFrom(temp, content).get(0));
                        ((forOneRecipeController) loader.getController()).setSceneProduct(GetText.getScene());
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
    private Hyperlink goToSearch;
    @FXML
    void initialize() {
        try {
            //setRecipes("recipes");
        } catch (Exception e) {
            e.printStackTrace();
        }
        goToSearch.setOnAction(event -> {
            FXMLLoader loader = LoadXML.load("ForSearch.fxml");
            Parent root = loader.getRoot();
            ((Stage) VBox.getScene().getWindow()).setScene(new Scene(root));
        });
        ButtonFind.setOnAction(event -> {
            links.clear();
            VBox.getChildren().clear();
            listView.getItems().clear();
            try {
                ArrayList<ArrayList<String>> output = Query.getByNamePrefix("recipes", GetText.getText());
                for (ArrayList<String> s : output) {
                    if (!s.get(4).equals("description")) {
                        Hyperlink link = new Hyperlink(s.get(1));
                        link.setTooltip(new Tooltip(
                                "Calories per 100 g: " + s.get(3)+ "\n" + "Description: " + s.get(4)));
                        link.setOnAction(t -> {
                            FXMLLoader loader = LoadXML.load("ForOneRecipeView.fxml");
                            ArrayList<ArrayList<String>> temp = new ArrayList<>();
                            temp.add(output.get(0));
                            temp.add(s);
                            try {
                                ArrayList<Pair<Integer,Integer>> content = Query.getAllContentOfRecipe(Integer.parseInt(s.get(0)));
                                ((forOneRecipeController) loader.getController()).setRecipe(Parser.getRecipesFrom(temp, content).get(0));
                                ((forOneRecipeController) loader.getController()).setSceneProduct(GetText.getScene());
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
        });
        caloriesAsc.setOnAction(event -> {
            try {
                setRecipes("(select * from recipes order by calories) a");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        caloriesDesc.setOnAction(event -> {
            try {
                setRecipes("(select * from recipes order by calories DESC) a");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
