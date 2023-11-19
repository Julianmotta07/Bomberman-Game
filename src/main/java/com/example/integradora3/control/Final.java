package com.example.integradora3.control;

import com.example.integradora3.MainMenu;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Final implements Initializable {

    @FXML
    private Button tryAgain;

    @FXML
    protected void tryButton() {

        MainMenu.showWindow("game-view", null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void restartGame() {

        MainMenu.showWindow("screenView/hello-view", (Stage) tryAgain.getScene().getWindow());
    }
}
