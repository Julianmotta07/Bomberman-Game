package com.example.integradora3.model;

import com.example.integradora3.MainMenu;
import javafx.scene.image.Image;

public class Explosion extends Entity{

    public Explosion(int x, int y) {
        super(x,y,29,29);
        sprite = new Image("file:"+ MainMenu.getFile("images/explosion_effect.png").getPath());
    }

}
