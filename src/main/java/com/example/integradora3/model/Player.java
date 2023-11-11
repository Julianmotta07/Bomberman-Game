package com.example.integradora3.model;

import com.example.integradora3.KeyboardControl;
import com.example.integradora3.MainMenu;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class Player extends MovableEntity{

    private boolean spacePressedLastFrame = false;
    private Image up1, up2, down1, down2, left1, left2, right1, right2, stop, heart;
    private String direction;
    private int spriteCounter;
    private int spriteNum;
    private int lives;
    private int maxBombs;
    private int bombsLevel;
    private int activeBombs;
    private ArrayList<Bomb> bombs;
    private int speedPowerUpTime;
    private int strongPowerUpTime;

    public Player(ArrayList<Wall> walls){
        super(31, 31, walls);
        bombs = new ArrayList<>();
        direction = "down";
        spriteNum = 1;
        maxBombs = 1;
        activeBombs = 0;
        lives = 3;
        speedPowerUpTime = 420;
        strongPowerUpTime = 840;
        bombsLevel = 1;
        getPlayerImage();
    }

    public void decrementSpeedPowerUpTime(){
        if (speedPowerUpTime > 0) {
            speedPowerUpTime--;
        }
    }

    public void decrementStrongPowerUpTime(){
        if (strongPowerUpTime > 0){
            strongPowerUpTime--;
        }
    }

    public void getPlayerImage(){
        up1 = new Image("file:"+ MainMenu.getFile("images/b_up_1.png").getPath());
        up2 = new Image("file:"+MainMenu.getFile("images/b_up_2.png").getPath());
        down1 = new Image("file:"+MainMenu.getFile("images/b_down_1.png").getPath());
        down2 = new Image("file:"+MainMenu.getFile("images/b_down_2.png").getPath());
        right1 = new Image("file:"+MainMenu.getFile("images/b_right1.png").getPath());
        right2 = new Image("file:"+MainMenu.getFile("images/b_right2.png").getPath());
        left1 = new Image("file:"+MainMenu.getFile("images/b_left_1.png").getPath());
        left2 = new Image("file:"+MainMenu.getFile("images/b_left_2.png").getPath());
        stop = new Image("file:"+MainMenu.getFile("images/b_stop.png").getPath());
        heart = new Image("file:"+MainMenu.getFile("images/heart-black.png").getPath());
    }

    public void move() {

        boolean upPressed = KeyboardControl.upPressed;
        boolean downPressed = KeyboardControl.downPressed;
        boolean leftPressed = KeyboardControl.leftPressed;
        boolean rightPressed = KeyboardControl.rightPressed;
        boolean spacePressed = KeyboardControl.spacePressed;

        if (upPressed || downPressed || leftPressed || rightPressed || spacePressed){

            if (upPressed) {
                if (checkObstacleCollision(1, walls)){
                    y -= speed;
                }
                direction = "up";
            } else if (downPressed) {
                if (checkObstacleCollision(2, walls)){
                    y += speed;
                }
                direction = "down";
            }
            if (leftPressed) {
                if (checkObstacleCollision(3, walls)){
                    x -= speed;
                }
                direction = "left";
            } else if (rightPressed) {
                if (checkObstacleCollision(4, walls)){
                    x += speed;
                }
                direction = "right";
            }

            if (spacePressed && !spacePressedLastFrame){
                if (activeBombs < maxBombs){
                    dropBomb();
                }
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

        spacePressedLastFrame = spacePressed;

    }

    public void dropBomb() {
        Bomb bomb = new Bomb(x, y, bombsLevel);
        bombs.add(bomb);
        activeBombs = activeBombs + 1;
    }

    public void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        Image image = null;
        switch (direction) {
            case "up" -> {
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
            }
            case "down" -> {
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
            }
            case "left" -> {
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
            }
            case "right" -> {
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
            }
            default -> image = stop;
        }
        gc.drawImage(image, x, y, width, height);
        drawHearts(gc);
    }

    private void drawHearts(GraphicsContext gc){
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.valueOf("#eb240efb"));
        gc.fillRect(38, 3, 78, 22);
        gc.strokeRect(38, 3, 78, 22);
        int x = 42;
        for (int i = 0; i < lives; i++){
            gc.drawImage(heart, x, 6, 16, 16);
            x += 27;
        }
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getBombsLevel(){
        return bombsLevel;
    }

    public void setBombsLevel(int bombsLevel){
        this.bombsLevel = bombsLevel;
    }

    public void setX(int x) {this.x = x;}
    public void setY(int y) {
        this.y = y;
    }

    public int getStrongPowerUpTime() {
        return strongPowerUpTime;
    }

    public void setStrongPowerUpTime(int strongPowerUpTime) {
        this.strongPowerUpTime = strongPowerUpTime;
    }

    public int getSpeedPowerUpTime() {
        return speedPowerUpTime;
    }

    public void setSpeedPowerUpTime(int speedPowerUpTime) {
        this.speedPowerUpTime = speedPowerUpTime;
    }

    public int getMaxBombs() {
        return maxBombs;
    }

    public void setMaxBombs(int maxBombs) {
        this.maxBombs = maxBombs;
    }

    public int getActiveBombs() {
        return activeBombs;
    }

    public void setActiveBombs(int activeBombs) {
        this.activeBombs = activeBombs;
    }

    public ArrayList<Bomb> getBombs(){
        return bombs;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
}

