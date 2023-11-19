package com.example.integradora3.control;

import com.example.integradora3.MainMenu;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Final {

    @FXML
    private Button exitButton;

    @FXML
    private Button replayButton;

    public  void finalGame(){

        MainMenu.showWindow("final-view",null);
    }

    @FXML
    public void setReplayButton() {

        MainMenu.hideWindow((Stage)replayButton.getScene().getWindow());
        MainMenu.showWindow("hello-view", (Stage) replayButton.getScene().getWindow());
    }

    @FXML
    public void onExitButton(){

        MainMenu.hideWindow((Stage)exitButton.getScene().getWindow());
        Platform.exit();
    }


}
