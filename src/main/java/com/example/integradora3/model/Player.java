package com.example.integradora3.model;

import com.example.integradora3.KeyboardControl;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Player {

    private GraphicsContext gc;
    private Image up1, up2, down1, down2, left1, left2, right1, right2, stop;
    private String direction;
    private int spriteCounter;
    private int spriteNum;
    private int x;
    private int y;
    private int speed;
    private ArrayList<Obstacle> obstacles;

    public Player(GraphicsContext gc, ArrayList<Obstacle> obstacles) {
        x = 31;
        y = 29;
        speed = 2;
        direction = "down";
        spriteNum = 1;
        this.gc = gc;
        this.obstacles = obstacles;
        getPlayerImage();
    }

    public void getPlayerImage(){
        up1 = new Image("file:b_up_1.png");
        up2 = new Image("file:b_up_2.png");
        down1 =new Image("file:b_down_1.png");
        down2 =new Image("file:b_down_2.png");
        right1 =new Image("file:b_right1.png");
        right2 = new Image("file:b_right2.png");
        left1= new Image("file:b_left_1.png");
        left2 = new Image("file:b_left_2.png");
        stop = new Image("file:b_stop.png");
    }


    public void run() {
        while (true) {
            move();
            draw();
            drawObstacles();
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void drawObstacles() {
        for (Obstacle obstacle : obstacles) {
            if (obstacle instanceof DestructibleWall destructibleWall){
                destructibleWall.draw();
            }
        }
    }

    public void move() {
        boolean upPressed = KeyboardControl.upPressed;
        boolean downPressed = KeyboardControl.downPressed;
        boolean leftPressed = KeyboardControl.leftPressed;
        boolean rightPressed = KeyboardControl.rightPressed;

        if (upPressed || downPressed || leftPressed || rightPressed){

            if (upPressed) {
                if (!checkObstacleCollision(1)){
                    y -= speed;
                }
                direction = "up";
            } else if (downPressed) {
                if (!checkObstacleCollision(2)){
                    y += speed;
                }
                direction = "down";
            }
            if (leftPressed) {
                if (!checkObstacleCollision(3)){
                    x -= speed;
                }
                direction = "left";
            } else if (rightPressed) {
                if (!checkObstacleCollision(4)){
                    x += speed;
                }
                direction = "right";
            }

            spriteCounter++;
            if(spriteCounter > 10){
                if (spriteNum == 1){
                    spriteNum = 2;
                } else if (spriteNum == 2){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        } else {
            direction = "none";
        }

    }

    public void draw() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        Image image = null;
        switch (direction) {
            case "up":
                if (spriteNum == 1){
                    image = up1;
                }
                if (spriteNum == 2){
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1){
                    image = down1;
                }
                if (spriteNum == 2){
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1){
                    image = left1;
                }
                if (spriteNum == 2){
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1){
                    image = right1;
                }
                if (spriteNum == 2){
                    image = right2;
                }
                break;
            default:
                image = stop;
                break;
        }
        gc.drawImage(image, x, y, 25, 25);
    }



    private boolean checkObstacleCollision(int direction) {
        for (Obstacle obstacle : obstacles) {
            switch (direction) {
                case 1 -> {
                    if (y - speed < obstacle.getY() + obstacle.getHeight() && y > obstacle.getY() &&
                            x + 25 > obstacle.getX() && x < obstacle.getX() + obstacle.getWidth()) {
                        return true;
                    }
                }
                case 2 -> {
                    if (y + 25 + speed > obstacle.getY() && y < obstacle.getY() &&
                            x + 25 > obstacle.getX() && x < obstacle.getX() + obstacle.getWidth()) {
                        return true;
                    }
                }
                case 3 -> {
                    if (x - speed < obstacle.getX() + obstacle.getWidth() && x > obstacle.getX() &&
                            y + 25 > obstacle.getY() && y < obstacle.getY() + obstacle.getHeight()) {
                        return true;
                    }
                }
                default -> {
                    if (x + speed + 25 > obstacle.getX() && x + speed < obstacle.getX() + obstacle.getWidth() &&
                            y + 25 > obstacle.getY() && y < obstacle.getY() + obstacle.getHeight()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}

