package com.example.integradora3.model;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class ObstaclesThread extends Thread {
    private GraphicsContext gc;
    private ArrayList<Obstacle> obstacles;

    public ObstaclesThread(GraphicsContext gc, ArrayList<Obstacle> obstacles) {
        this.gc = gc;
        this.obstacles = obstacles;
    }

    @Override
    public void run() {
        while (true) {
            renderObstacles();
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void renderObstacles() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        for (Obstacle obstacle : obstacles) {
            if (obstacle instanceof DestructibleWall dw) {
                dw.draw();
            }
        }
    }
}

