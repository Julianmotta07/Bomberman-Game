package com.example.integradora3.model;

import com.example.integradora3.MainMenu;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;

public class Bomb extends Entity{

    int explosionRadius;
    private int timer;
    private int explosionTimer;
    private final int[] explosionDirections;
    private final ArrayList<Explosion> explosions;
    private boolean hasExploded;

    public Bomb(int x, int y, int bombLevel) {
        super(x,y,29,29);
        explosionRadius = bombLevel == 1 ? 32 : 64;
        explosions = new ArrayList<>();
        sprite = new Image("file:" + MainMenu.getFile("images/bomb.png").getPath());
        timer = 180;
        explosionTimer = 0;
        explosionDirections = new int[4];
        hasExploded = false;
        adjustCoordinate();
    }

    private void adjustCoordinate() {
        int[] nearestCoordinate = new int[2];
        double closestDistance = Double.MAX_VALUE;
        ArrayList<int[]> allowedCoordinates = generateAllowedCoordinates();
        for (int[] coordinate : allowedCoordinates) {
            double distance = Math.sqrt(Math.pow(coordinate[0] - x, 2) + Math.pow(coordinate[1] - y, 2));
            if (distance < closestDistance) {
                closestDistance = distance;
                nearestCoordinate = coordinate;
            }
        }
        x = nearestCoordinate[0];
        y = nearestCoordinate[1];
    }

    private ArrayList<int[]> generateAllowedCoordinates() {
        int minX = 31, minY = 31, maxX = 899, maxY = 341, stepX = 31, stepY = 31;
        ArrayList<int[]> allowedCoordinates = new ArrayList<>();
        for (int x = minX; x <= maxX; x += stepX) {
            for (int y = minY; y <= maxY; y += stepY) {
                int[] coordinate = {x, y};
                allowedCoordinates.add(coordinate);
            }
        }
        return allowedCoordinates;
    }

    public void draw(GraphicsContext gc) {
        if (explosionTimer > 0) {
            if (explosionDirections[0] != 1) {
                if (explosionRadius != 32){
                    addExplosion(x,y - 58);
                }
                addExplosion(x,y - 29);
            }
            if (explosionDirections[1] != 1) {
                if (explosionRadius != 32){
                    addExplosion(x,y + 58);
                }
                addExplosion(x,y + 29);
            }
            if (explosionDirections[2] != 1) {
                if (explosionRadius != 32){
                    addExplosion(x - 58,y);
                }
                addExplosion(x - 29,y);
            }
            if (explosionDirections[3] != 1) {
                if (explosionRadius != 32){
                    addExplosion(x + 58,y);
                }
                addExplosion(x + 29,y);
            }
            for (Explosion explosion : explosions){
                explosion.draw(gc);
            }
        } else {
            gc.drawImage(sprite, x, y, width, height);
        }
    }

    private void addExplosion(int x, int y){
        explosions.add(new Explosion(x,y));
    }

    public void decrementTimer() {
        if (timer > 0) {
            timer--;
        }
        if (explosionTimer > 0) {
            explosionTimer--;
        }
    }

    public void explode(ArrayList<Wall> walls, ArrayList<PowerUp> powerUps, int currentPlayerBombs) {
        List<Wall> obstaclesToRemove = new ArrayList<>();
        for (Wall wall : walls) {
            if (isWithinExplosionRadius(wall)){
                if (wall instanceof DestructibleWall){
                    if (Math.random() < 0.2) {
                        int random = (int) (currentPlayerBombs >= 5 ? (Math.random() * 2) + 1 : (Math.random() * 3) + 1);
                        powerUps.add(new PowerUp(wall.getX() + 3, wall.getY() + 3, random));
                    }
                    obstaclesToRemove.add(wall);
                } else {
                    int direction = checkObstacleDirection(wall);
                    if (direction != 0 && explosionDirections[direction - 1] == 0) {
                        explosionDirections[direction - 1] = 1;
                    }
                }
            }
        }
        explosionTimer = 60;
        hasExploded = true;
        walls.removeAll(obstaclesToRemove);
    }

    private int checkObstacleDirection(Wall wall) {
        double obstacleX = wall.getX();
        double obstacleY = wall.getY();
        double obstacleWidth = wall.getWidth();
        double obstacleHeight = wall.getHeight();
        double bombCenterX = x + 14.5;
        double bombCenterY = y + 14.5;

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

    private boolean isWithinExplosionRadius(Wall wall) {
        int dx = (wall.getX() - x);
        int dy = (wall.getY() - y);
        int distance = (int) Math.sqrt(dx * dx + dy * dy);
        return distance <= explosionRadius;
    }

    public int getExplosionTimer() {
        return explosionTimer;
    }

    public int getTimer() {
        return timer;
    }

    public boolean hasExploded() {
        return hasExploded;
    }

    public ArrayList<Explosion> getExplosions(){
        return explosions;
    }

}
