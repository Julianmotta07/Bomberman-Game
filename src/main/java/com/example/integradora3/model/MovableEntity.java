package com.example.integradora3.model;

import java.util.ArrayList;

public abstract class MovableEntity extends Entity{

    protected int speed;
    protected int spriteCounter;
    protected int spriteNum;

    MovableEntity(int x, int y){
        super(x,y,32,32);
        speed = 1;
        spriteNum = 1;
    }

    protected boolean checkObstacleCollision(int direction, ArrayList<Entity> obstacles) {
        int size = 30;
        for (Entity obstacle : obstacles) {
            switch (direction) {
                case 1 -> {
                    if (y - speed < obstacle.y + obstacle.height && y > obstacle.y &&
                            x + size > obstacle.x && x < obstacle.x + obstacle.width) {
                        return false;
                    }
                }
                case 2 -> {
                    if (y + size + speed > obstacle.y && y < obstacle.y &&
                            x + size > obstacle.x && x < obstacle.x + obstacle.width) {
                        return false;
                    }
                }
                case 3 -> {
                    if (x - speed < obstacle.x + obstacle.width && x > obstacle.x &&
                            y + size > obstacle.y && y < obstacle.y + obstacle.height) {
                        return false;
                    }
                }
                default -> {
                    if (x + speed + size > obstacle.x && x + speed < obstacle.x + obstacle.width &&
                            y + size > obstacle.y && y < obstacle.y + obstacle.height) {
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

        double otherEntityCenterX = otherEntity.x + ((double) otherEntity.width / 2); // Centro de la otra entidad en el eje X
        double otherEntityCenterY = otherEntity.y + ((double) otherEntity.height / 2); // Centro de la otra entidad en el eje Y

        double distance = Math.sqrt(Math.pow(thisEntityCenterX - otherEntityCenterX, 2) + Math.pow(thisEntityCenterY - otherEntityCenterY, 2));

        // distancia máxima para considerar una colisión
        double maxCollisionDistance = 20.0;

        return distance <= maxCollisionDistance;
    }
}
