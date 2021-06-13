package main.Application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.awt.event.ActionEvent;

public class ForSearchController extends Main {

    @FXML
    private ImageView home;

    @FXML
    private TextField GetText;

    @FXML
    private Button ButtonFind;

    @FXML
    private Button cancelButton;

    @FXML
    private Button settings;

    @FXML
    void goToHome(ActionEvent event) {
        Main.goToHome();
    }

    @FXML
    void openSettings(ActionEvent event) {

    }

}

