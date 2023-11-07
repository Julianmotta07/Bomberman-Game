package com.example.integradora3.model;

import com.example.integradora3.KeyboardControl;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class Player {

    private boolean spacePressedLastFrame = false;
    private GraphicsContext gc;
    private Image up1, up2, down1, down2, left1, left2, right1, right2, stop, heart;
    private String direction;
    private int spriteCounter;
    private int spriteNum;
    private int x;
    private int y;
    private int lifes;
    private int speed;
    private int maxBombs;
    private int activeBombs;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<Bomb> bombs;
    private int speedPowerUpTime;

    public Player(GraphicsContext gc, ArrayList<Obstacle> obstacles) {
        bombs = new ArrayList<>();
        x = 32;
        y = 32;
        speed = 1;
        direction = "down";
        spriteNum = 1;
        this.gc = gc;
        this.obstacles = obstacles;
        getPlayerImage();
        maxBombs = 1;
        activeBombs = 0;
        lifes = 3;
        speedPowerUpTime = 420;
    }

    public void decrementTimer() {
        if (speedPowerUpTime > 0) {
            speedPowerUpTime--;
        }
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
        heart = new Image("file:heart-black.png");
    }

    public void move() {

        boolean upPressed = KeyboardControl.upPressed;
        boolean downPressed = KeyboardControl.downPressed;
        boolean leftPressed = KeyboardControl.leftPressed;
        boolean rightPressed = KeyboardControl.rightPressed;
        boolean spacePressed = KeyboardControl.spacePressed;

        if (upPressed || downPressed || leftPressed || rightPressed || spacePressed){

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

            if (spacePressed && !spacePressedLastFrame){
                if (activeBombs < maxBombs){
                    List<int[]> coordenadasPermitidas = generarCoordenadasPermitidas(31, 31, 899, 341, 31, 31);
                    int[] coordenadaMasCercana = calcularCoordenadaMasCercana(coordenadasPermitidas, x, y);
                    soltarBomba(coordenadaMasCercana);
                    activeBombs = activeBombs + 1;
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

    public int getActiveBombs() {
        return activeBombs;
    }

    public void setActiveBombs(int activeBombs) {
        this.activeBombs = activeBombs;
    }

    public ArrayList<Bomb> getBombs(){
        return bombs;
    }

    public void soltarBomba(int[] coord) {
        Bomb bomb = new Bomb(coord[0], coord[1], gc);
        bombs.add(bomb);
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
        gc.drawImage(image, x, y, 30, 29);
        drawhearts();
    }

    private void drawhearts(){
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.valueOf("#eb240efb"));
        gc.fillRect(38, 3, 78, 22);
        gc.strokeRect(38, 3, 78, 22);
        int x = 42;
        for (int i = 0; i < lifes; i++){
            gc.drawImage(heart, x, 6, 16, 16);
            x+=27;
        }
    }


    private boolean checkObstacleCollision(int direction) {
        for (Obstacle obstacle : obstacles) {
            switch (direction) {
                case 1 -> {
                    if (y - speed < obstacle.getY() + obstacle.getHeight() && y > obstacle.getY() &&
                            x + 28 > obstacle.getX() && x < obstacle.getX() + obstacle.getWidth()) {
                        return true;
                    }
                }
                case 2 -> {
                    if (y + 28 + speed > obstacle.getY() && y < obstacle.getY() &&
                            x + 28 > obstacle.getX() && x < obstacle.getX() + obstacle.getWidth()) {
                        return true;
                    }
                }
                case 3 -> {
                    if (x - speed < obstacle.getX() + obstacle.getWidth() && x > obstacle.getX() &&
                            y + 28 > obstacle.getY() && y < obstacle.getY() + obstacle.getHeight()) {
                        return true;
                    }
                }
                default -> {
                    if (x + speed + 27 > obstacle.getX() && x + speed < obstacle.getX() + obstacle.getWidth() &&
                            y + 27 > obstacle.getY() && y < obstacle.getY() + obstacle.getHeight()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int[] calcularCoordenadaMasCercana(List<int[]> coordenadasPermitidas, int x, int y) {
        int[] coordenadaMasCercana = null;
        double distanciaMasCercana = Double.MAX_VALUE;

        for (int[] coordenada : coordenadasPermitidas) {
            double distancia = Math.sqrt(Math.pow(coordenada[0] - x, 2) + Math.pow(coordenada[1] - y, 2));


            if (distancia < distanciaMasCercana) {
                distanciaMasCercana = distancia;
                coordenadaMasCercana = coordenada;
            }
        }
        return coordenadaMasCercana;
    }


    public List<int[]> generarCoordenadasPermitidas(int minX, int minY, int maxX, int maxY, int stepX, int stepY) {
        List<int[]> coordenadasPermitidas = new ArrayList<>();

        for (int x = minX; x <= maxX; x += stepX) {
            for (int y = minY; y <= maxY; y += stepY) {
                int[] coordenada = {x, y};
                coordenadasPermitidas.add(coordenada);
            }
        }
        return coordenadasPermitidas;
    }

    public boolean touches(Item item) {
        double playerCenterX = x + (29 / 2); // Calcula el centro del jugador en el eje X
        double playerCenterY = y + (29 / 2); // Calcula el centro del jugador en el eje Y

        double itemCenterX = item.getX() + (20 / 2); // Calcula el centro del ítem en el eje X
        double itemCenterY = item.getY() + (20 / 2); // Calcula el centro del ítem en el eje Y

        double distancia = Math.sqrt(Math.pow(playerCenterX - itemCenterX, 2) + Math.pow(playerCenterY - itemCenterY, 2));

        // distancia máxima para considerar una colisión
        double distanciaMaximaParaColision = 20.0;

        return distancia <= distanciaMaximaParaColision;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}

