package com.example.integradora3.control;

import com.example.integradora3.MainMenu;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class GameOver {

    @FXML
    private Button restart;

    @FXML
    private Button exitButton;

    public  void loseGame(){

        MainMenu.showWindow("gameOver-view",null);
    }
    @FXML
    public void onRestartButton() {

        MainMenu.hideWindow((Stage)restart.getScene().getWindow());
        MainMenu.showWindow("hello-view", (Stage) restart.getScene().getWindow());
    }

    @FXML
    public void onExitButton(){

        MainMenu.hideWindow((Stage)exitButton.getScene().getWindow());
        Platform.exit();
    }


}
