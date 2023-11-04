package com.example.demojavafx;

import com.example.demojavafx.model.User;
import com.google.gson.Gson;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        showWindow("login-view", stage);
    }

    public static void showWindow(String fxml, Stage stage){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxml+".fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 320, 240);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(stage==null) stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public static void hideWindow(Stage stage){
        stage.hide();
    }

    public static void main(String[] args) {
        Gson gson = new Gson(); // just to test importing Gson from pom
        launch(); // invoca el metodo start() y deja un hilo corriendo hasta que la ventana se cierre
        System.out.println("fin de main");  // esta linea solo se ejectua cuando el hilo anterior haya terminado
    }
}