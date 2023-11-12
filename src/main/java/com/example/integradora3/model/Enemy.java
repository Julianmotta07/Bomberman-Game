package com.example.integradora3.model;

import com.example.integradora3.MainMenu;
import javafx.scene.image.Image;
import java.util.ArrayList;

public class Enemy extends MovableEntity{
    private int framesToMove = 0;

    public Enemy(int x, int y, int stage) {
        super(x,y);
        switch (stage) {
            case 1 -> sprite = new Image("file:" + MainMenu.getFile("images/enemy_1.png").getPath());
            case 2 -> sprite = new Image("file:" + MainMenu.getFile("images/enemy_2.png").getPath());
            default -> sprite = new Image("file:" + MainMenu.getFile("images/enemy_3.png").getPath());
        }
    }

    public void moveTowardsPlayer(Player player, ArrayList<Wall> walls) {
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
