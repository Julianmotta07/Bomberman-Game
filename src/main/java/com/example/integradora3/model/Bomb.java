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
    private Image bomb, explosion;
    private int explosionTimer;
    private static final int EXPLOSION_DURATION = 500;
    private int[] explosionDirections;
    private boolean hasExploted;

    public Bomb(int x, int y, GraphicsContext gc) {
        this.x = x;
        this.y = y;
        this.gc = gc;
        explosionRadius = 32;
        getBombImage();
        timer = 180;
        explosionTimer = 0;
        explosionDirections = new int[4];
        hasExploted = false;
    }

    public boolean isExplosionVisible() {
        return explosionTimer > 0;
    }

    private void getBombImage() {
        bomb = new Image("file:bomb.png");
        explosion = new Image("file:explosion_effect.png");
    }

    public void draw() {
        if (explosionTimer > 0) {
            if (explosionDirections[0] != 1) {
                gc.drawImage(explosion, x, y - 29, 29, 29);
            }
            if (explosionDirections[1] != 1) {
                gc.drawImage(explosion, x, y + 29, 29, 29);
            }
            if (explosionDirections[2] != 1) {
                gc.drawImage(explosion, x - 29, y, 29, 29);
            }
            if (explosionDirections[3] != 1) {
                gc.drawImage(explosion, x + 29, y, 29, 29);
            }
        } else {
            gc.drawImage(bomb, x, y, 29, 29);
        }
    }

    public void decrementTimer() {
        if (timer > 0) {
            timer--;
        }
    }

    public int getExplosionTimer() {
        return explosionTimer;
    }

    public void update() {
        if (explosionTimer > 0) {
            explosionTimer--;
        }
    }

    public int getTimer() {
        return timer;
    }

    public boolean hasExploted() {
        return hasExploted;
    }

    public void expld(ArrayList<Obstacle> obstacles, ArrayList<Item> items) {
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


    public void explode(ArrayList<Obstacle> obstacles, ArrayList<Item> items) {
        List<Obstacle> obstaclesToRemove = new ArrayList<>();
        for (Obstacle obstacle : obstacles) {
            if (isWithinExplosionRadius(obstacle)){
                if (obstacle instanceof DestructibleWall){
                    if (Math.random() < 0.6) {
                        items.add(new Item((int) obstacle.getX() + 3, (int) obstacle.getY() + 3, (int) (Math.random() * 3) + 1));
                    }
                    obstaclesToRemove.add(obstacle);
                } else {
                    int direction = checkObstacleDirection(obstacle);
                    if (direction != 0 && explosionDirections[direction - 1] == 0) {
                        explosionDirections[direction - 1] = 1;
                    }
                }
            }
        }

        explosionTimer = 60;
        hasExploted = true;

        obstacles.removeAll(obstaclesToRemove);
    }

    private int checkObstacleDirection(Obstacle obstacle) {
        double obstacleX = obstacle.getX();
        double obstacleY = obstacle.getY();
        double obstacleWidth = obstacle.getWidth();
        double obstacleHeight = obstacle.getHeight();
        double bombCenterX = x + 14.5; // Centro de la bomba en X
        double bombCenterY = y + 14.5; // Centro de la bomba en Y

        // Verificar colisión en las cuatro direcciones
        if (bombCenterY - explosionRadius < obstacleY + obstacleHeight &&
                bombCenterY > obstacleY &&
                bombCenterX + 14.5 > obstacleX &&
                bombCenterX - 14.5 < obstacleX + obstacleWidth) {
            // Colisión en la dirección arriba
            return 1;
        } else if (bombCenterY + explosionRadius > obstacleY &&
                bombCenterY < obstacleY &&
                bombCenterX + 14.5 > obstacleX &&
                bombCenterX - 14.5 < obstacleX + obstacleWidth) {
            // Colisión en la dirección abajo
            return 2;
        } else if (bombCenterX - explosionRadius < obstacleX + obstacleWidth &&
                bombCenterX > obstacleX &&
                bombCenterY + 14.5 > obstacleY &&
                bombCenterY - 14.5 < obstacleY + obstacleHeight) {
            // Colisión en la dirección izquierda
            return 3;
        } else if (bombCenterX + explosionRadius > obstacleX &&
                bombCenterX < obstacleX &&
                bombCenterY + 14.5 > obstacleY &&
                bombCenterY - 14.5 < obstacleY + obstacleHeight) {
            // Colisión en la dirección derecha
            return 4;
        }

        // No hay colisión en ninguna dirección
        return 0;
    }

    private boolean isWithinExplosionRadius(Obstacle obstacle) {
        int dx = (int) (obstacle.getX() - x);
        int dy = (int) (obstacle.getY() - y);
        int distance = (int) Math.sqrt(dx * dx + dy * dy);
        return distance <= explosionRadius;
    }

}
