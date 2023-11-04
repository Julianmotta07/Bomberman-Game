package com.example.demojavafx.control;

import com.example.demojavafx.HelloApplication;
import com.example.demojavafx.model.User;
import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private Button logOutButton;

    @FXML
    private Label usernameLabel;

    @FXML
    private ChoiceBox difficultyCB;

    public HomeController() {
        // FXML resources are null here
    }

    @FXML
    public void onLogOutClick(){
        HelloApplication.hideWindow((Stage)logOutButton.getScene().getWindow());
        HelloApplication.showWindow("login-view", null);
        System.out.println(difficultyCB.getValue());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // FXML resources are already set here
        User user = User.getInstance();
        String message = MessageFormat.format("Welcome, {0}", user.getUsername());
        usernameLabel.setText(message);
        difficultyCB.getItems().add("Easy");
        difficultyCB.getItems().add("Hard");
    }
}