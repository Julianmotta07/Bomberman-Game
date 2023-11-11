package com.example.integradora3.model;

import com.example.integradora3.MainMenu;
import javafx.scene.image.Image;
import java.util.ArrayList;

public class Enemy extends MovableEntity{
    private int framesToMove = 0;

    public Enemy(int x, int y, ArrayList<Wall> walls) {
        super(x,y, walls);
        sprite = new Image("file:" + MainMenu.getFile("images/enemy_1.png").getPath());
    }

    public void moveTowardsPlayer(Player player) {
        framesToMove++;
        int desiredSpeed = 2;
        if (framesToMove >= desiredSpeed) {

            if (player.getX() < x && checkObstacleCollision(3, walls)) {
                x -= speed;
            } else if (player.getX() > x && checkObstacleCollision(4, walls)) {
                x += speed;
            }

            if (player.getY() < y && checkObstacleCollision(1, walls)) {
                y -= speed;
            } else if (player.getY() > y && checkObstacleCollision(2, walls)) {
                y += speed;
            }
            framesToMove = 0;
        }

    }
}
