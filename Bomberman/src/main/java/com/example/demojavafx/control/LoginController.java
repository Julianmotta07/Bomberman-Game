package com.example.demojavafx.control;

import com.example.demojavafx.HelloApplication;
import com.example.demojavafx.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private CheckBox cbCaptcha;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordPF;

    @FXML
    protected void onLoginButtonClick() {
        String username = usernameTextField.getText();
        String password = passwordPF.getText();
        // validacion de username y password
        if(cbCaptcha.isSelected() && !username.isBlank() && !password.isBlank()){
            User.getInstance().setUsername(username);
            User.getInstance().setPassword(password);
            HelloApplication.hideWindow((Stage)cbCaptcha.getScene().getWindow());
            HelloApplication.showWindow("home-view", null);
        } else {
            // TODO
        }
    }
}