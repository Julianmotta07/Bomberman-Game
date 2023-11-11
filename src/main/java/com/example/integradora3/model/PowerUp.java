package com.example.integradora3.model;

import com.example.integradora3.MainMenu;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class PowerUp extends Entity{

    private PowerUpType type;

    public PowerUp(int x, int y, int r){
        super(x,y,25,25);
        switch (r) {
            case 1 -> {
                type = PowerUpType.BOMB;
                sprite = new Image("file:"+ MainMenu.getFile("images/item_bomb.png").getPath());
            }
            case 2 -> {
                type = PowerUpType.SPEED;
                sprite =  new Image("file:"+ MainMenu.getFile("images/item_speed.png").getPath());
            }
            default -> {
                type = PowerUpType.STRONG;
                sprite = new Image("file:"+ MainMenu.getFile("images/item_bomb_power.png").getPath());
            }
        }
    }

    public PowerUpType getType() {
        return type;
    }

    public Image getSpite(){
        return sprite;
    }

    public void draw(GraphicsContext gc){

        gc.drawImage(sprite,x,y,width,height);
    }
}
