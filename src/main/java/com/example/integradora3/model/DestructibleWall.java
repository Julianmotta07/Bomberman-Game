package com.example.integradora3.model;

import com.example.integradora3.MainMenu;
import javafx.scene.image.Image;

public class DestructibleWall extends Wall {

    public DestructibleWall(int x, int y, int width, int height, int stage) {
        super(x, y, width, height);
        sprite = new Image("file:"+ MainMenu.getFile("images/wall_"+stage+".png").getPath());
    }

}