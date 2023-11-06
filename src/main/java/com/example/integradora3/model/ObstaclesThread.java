package com.example.integradora3.model;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class ObstaclesThread extends Thread{

    private ArrayList<Obstacle> obstacles;
    private GraphicsContext gc;

    public ObstaclesThread (ArrayList<Obstacle> obstacles, GraphicsContext gc){
        this.obstacles = obstacles;
        this.gc = gc;
    }

    @Override
    public void run() {
        while (true) {
            gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

            for (Obstacle obstacle : obstacles) {
                    if (obstacle instanceof DestructibleWall dw){
                        dw.draw();
                }
            }
        }
    }
}
