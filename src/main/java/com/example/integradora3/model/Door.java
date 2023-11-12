package com.example.integradora3.model;

import com.example.integradora3.MainMenu;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Door extends Entity {

    private boolean isOpen;

    public Door(int x, int y, boolean isOpen) {
        super(x, y, 28, 28);
        this.isOpen = isOpen;
        sprite = new Image("file:"+ MainMenu.getFile("images/door.png").getPath());
    }

    public void draw(GraphicsContext gc){
        if (isOpen){
            gc.drawImage(sprite,x,y,width,height);
        }
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}