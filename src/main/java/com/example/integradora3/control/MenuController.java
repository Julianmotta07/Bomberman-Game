package com.example.integradora3.control;
import com.example.integradora3.MainMenu;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MenuController {

    @FXML
    private Label tittle;
    @FXML
    protected void playButton() {
            MainMenu.hideWindow((Stage)tittle.getScene().getWindow());
            MainMenu.showWindow("game-view", null);
    }

}