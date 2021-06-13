package main.Application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class query1Controller {
    @FXML
    private Button Back;
    void setSceneBack(Scene scene){
        Back.setOnAction(t -> {
            ((Stage) Back.getScene().getWindow()).setScene(scene);
        });
    }
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private AnchorPane pane;

    @FXML
    private TextArea Area;
    String addEnters(String s) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < s.length(); ++i) {
            res.append(s.charAt(i));
            if (s.charAt(i) == ']') {
                res.append('\n');
                i += 3;
            }
        }
        return res.toString();
    }
    void setText(String s){
        Area.setText(addEnters(s));
    }
    @FXML
    void initialize() {
    }
}
