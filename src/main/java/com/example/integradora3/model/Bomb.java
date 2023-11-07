package com.example.integradora3.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Bomb {

    private GraphicsContext gc;
    int explosionRadius;
    private int x;
    private int y;
    private int timer;
    private Image bomb;

    public Bomb(int x,int y, GraphicsContext gc){
        this.x = x;
        this.y = y;
        this.gc = gc;
        explosionRadius = 32;
        getBombImage();
        timer = 180;
    }

    private void getBombImage(){
        bomb = new Image("file:bomb.png");
    }

    public void draw(){
        gc.drawImage(bomb,x,y,29,29);
    }

    public void decrementTimer() {
        if (timer > 0) {
            timer--;
        }
    }

    public int getTimer(){
        return timer;
    }

    public void explode(ArrayList<Obstacle> obstacles, ArrayList<Item> items) {
        List<Obstacle> obstaclesToRemove = new ArrayList<>();
        for (Obstacle obstacle : obstacles) {
            if (obstacle instanceof DestructibleWall) {
                if (isWithinExplosionRadius(obstacle)) {
                    if (Math.random() < 0.6) {
                        items.add(new Item((int) obstacle.getX() + 3, (int) obstacle.getY() + 3, (int) (Math.random() * 3) + 1));
                    }
                    obstaclesToRemove.add(obstacle);
                }
            }
        }
        obstacles.removeAll(obstaclesToRemove);
    }


    private boolean isWithinExplosionRadius(Obstacle obstacle) {
        int dx = (int) (obstacle.getX() - x);
        int dy = (int) (obstacle.getY() - y);
        int distance = (int) Math.sqrt(dx * dx + dy * dy);
        return distance <= explosionRadius;
    }
}
