package com.example.integradora3.model;

import com.example.integradora3.MainMenu;
import javafx.scene.image.Image;

public class Door extends Entity {

    public Door(int x, int y) {
        super(x, y, 28, 28);
        sprite = new Image("file:"+ MainMenu.getFile("images/door.png").getPath());
    }

}