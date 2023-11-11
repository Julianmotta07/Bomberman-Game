package com.example.integradora3.model;

import java.util.ArrayList;

public abstract class MovableEntity extends Entity{

    protected int speed;
    protected ArrayList<Wall> walls;

    MovableEntity(int x, int y, ArrayList<Wall> walls){
        super(x,y,32,32);
        this.walls = walls;
        speed = 1;
    }

    protected boolean checkObstacleCollision(int direction, ArrayList<Wall> walls) {
        for (Wall wall : walls) {
            switch (direction) {
                case 1 -> {
                    if (y - speed < wall.getY() + wall.getHeight() && y > wall.getY() &&
                            x + 30 > wall.getX() && x < wall.getX() + wall.getWidth()) {
                        return false;
                    }
                }
                case 2 -> {
                    if (y + 30 + speed > wall.getY() && y < wall.getY() &&
                            x + 30 > wall.getX() && x < wall.getX() + wall.getWidth()) {
                        return false;
                    }
                }
                case 3 -> {
                    if (x - speed < wall.getX() + wall.getWidth() && x > wall.getX() &&
                            y + 30 > wall.getY() && y < wall.getY() + wall.getHeight()) {
                        return false;
                    }
                }
                default -> {
                    if (x + speed + 30 > wall.getX() && x + speed < wall.getX() + wall.getWidth() &&
                            y + 30 > wall.getY() && y < wall.getY() + wall.getHeight()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean touches(Entity otherEntity) {
        double thisEntityCenterX = x + ((double) width / 2); // Centro de la entidad en el eje X
        double thisEntityCenterY = y + ((double) height / 2); // Centro de la entidad en el eje Y

        double otherEntityCenterX = otherEntity.getX() + ((double) otherEntity.getWidth() / 2); // Centro de la otra entidad en el eje X
        double otherEntityCenterY = otherEntity.getY() + ((double) otherEntity.getHeight() / 2); // Centro de la otra entidad en el eje Y

        double distance = Math.sqrt(Math.pow(thisEntityCenterX - otherEntityCenterX, 2) + Math.pow(thisEntityCenterY - otherEntityCenterY, 2));

        // distancia máxima para considerar una colisión
        double maxCollisionDistance = 20.0;

        return distance <= maxCollisionDistance;
    }
}
