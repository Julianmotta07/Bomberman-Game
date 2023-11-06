package com.example.integradora3.model;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class DestructibleWall extends Obstacle {
    private Image wall;
    private GraphicsContext gc;

    public DestructibleWall(double x, double y, double w, double h, GraphicsContext gc) {
        super(x, y, w, h);
        getWallImage();
        this.gc = gc;
    }

    public void getWallImage(){
        wall = new Image("file:wall.jpg");
    }

    public void draw(){
        gc.drawImage(wall,getX(),getY(),32,32);
    }

}