package com.example.integradora3.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Item {

    private int x;
    private int y;

    private Image item;
    private ItemType type;

    public Item(int x, int y, int r){
        this.x = x;
        this.y = y;
        switch (r) {
            case 1 -> {
                type = ItemType.BOMB;
                item = new Image("file:item_bomb.png");
            }
            case 2 -> {
                type = ItemType.SPEED;
                item = new Image("file:item_speed.png");
            }
            default -> {
                type = ItemType.STRONG;
                item = new Image("file:item_bomb_power.png");
            }
        }
    }

    public ItemType getType() {
        return type;
    }

    public Image getImage(){
        return item;
    }

    public void draw(GraphicsContext gc){
        gc.drawImage(item,x,y,25,25);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
