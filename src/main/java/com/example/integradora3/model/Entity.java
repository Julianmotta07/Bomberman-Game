package com.example.integradora3.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Entity {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Image sprite;

    public Entity(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    public void draw(GraphicsContext gc){
        gc.drawImage(sprite,x,y,width,height);
    }



    public int getX() {
        return x;
    }

    public int getY(){
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
