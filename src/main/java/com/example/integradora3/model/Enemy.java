package com.example.integradora3.model;

import com.example.integradora3.MainMenu;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.ArrayList;

public class Enemy extends MovableEntity{

    private final Image sprite2;
    private final Image sprite3;
    private int framesToMove = 0;

    public Enemy(int x, int y, int stage) {
        super(x, y);
        Image[] sprites = new Image[3];
        for (int i = 0; i < 3; i++) {
            sprites[i] = new Image("file:"+ MainMenu.getFile("images/enemy_"+stage+"_"+(i+1)+".png").getPath());
        }
        sprite = sprites[0];
        sprite2 = sprites[1];
        sprite3 = sprites[2];
    }


    public void moveTowardsPlayer(Player player, ArrayList<Entity> obstacles) {
        framesToMove++;
        int desiredSpeed = 2;
        if (framesToMove >= desiredSpeed) {
            if (player.getX() < x && checkObstacleCollision(3, obstacles)) {
                x -= speed;
            } else if (player.getX() > x && checkObstacleCollision(4, obstacles)) {
                x += speed;
            }

            if (player.getY() < y && checkObstacleCollision(1, obstacles)) {
                y -= speed;
            } else if (player.getY() > y && checkObstacleCollision(2, obstacles)) {
                y += speed;
            }
            framesToMove = 0;
        }

        spriteCounter++;
        if (spriteCounter > 10) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 3;
            } else if (spriteNum == 3) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(GraphicsContext gc) {
        Image image;
        if (spriteNum == 1) {
            image = sprite;
        } else if (spriteNum == 2){
            image = sprite2;
        } else {
            image = sprite3;
        }
        gc.drawImage(image, x, y, width, height);
    }

}
