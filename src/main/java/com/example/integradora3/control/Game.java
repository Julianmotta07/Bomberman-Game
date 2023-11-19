package com.example.integradora3.control;

import com.example.integradora3.KeyboardControl;
import com.example.integradora3.MainMenu;
import com.example.integradora3.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class Game implements Initializable {

    private int currentStage;
    @FXML
    private Canvas canvas;
    @FXML
    private Label bombsNum;
    private GraphicsContext gc;
    private Player player;
    private ArrayList<PowerUp> powerUps;
    private Image[] itemsBar;
    private Stage[] stages;
    private boolean gameOver = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(KeyboardControl::onKeyPressed);
        canvas.setOnKeyReleased(KeyboardControl::onKeyReleased);
        initializeGame();
        Thread gameThread = new Thread(() -> {
            while (!gameOver) {
                Platform.runLater(() -> {
                    updateBombTimers();
                    updatePowerUps();
                    moveEntities();
                    paintEntities();
                    checkPlayerCollisions();
                    checkEnemiesCollisions();
                });
                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        gameThread.start();
    }

    private void initializeGame(){
        currentStage = 3;
        powerUps = new ArrayList<>();
        player = new Player();
        bombsNum.setText("1");
        itemsBar = new Image[3];
        itemsBar[0] = new Image("file:" + MainMenu.getFile("images/bomb_icon.png").getPath());
        createStages();
    }

    private void createStages(){
        stages = new Stage[3];
        stages[0] = new Stage(createWalls(1),createEnemies(1),new Door(898,340,true));
        stages[1] = new Stage(createWalls(2),createEnemies(2),new Door(898,340,true));
        stages[2] = new Stage(createWalls(3),createEnemies(3),new Door(898,340,false));
    }

    private ArrayList<Wall> createWalls(int stage){
        ArrayList<Wall> walls = new ArrayList<>(createStaticWalls());
        walls.addAll(createDestructibleWalls(stage));
        return walls;
    }

    private ArrayList<Enemy> createEnemies(int stage){


        ArrayList<Enemy> enemies = new ArrayList<>();

        Random numberOfEnemies = new Random();

        int firstStageEnemies = numberOfEnemies.nextInt(5)+1;

        int secondStageEnemies = numberOfEnemies.nextInt(8)+1;

        int  thirdStageEnemies = numberOfEnemies.nextInt(11)+1;

       // System.out.println("enemigos aleatorios: "+thirdStageEnemies); Solo comprobaba que fueran aleatorios

        switch (stage) {

            case 1 -> {

                for (int i = 0; i <firstStageEnemies; i++) {

                    switch (i) {

                        case 0 -> enemies.add(new Enemy(400, 31, stage));
                        case 1 -> enemies.add(new Enemy(31, 340, stage));
                        case 2 -> enemies.add(new Enemy(649, 180, stage));
                        case 3 -> enemies.add(new Enemy(893, 31, stage));
                        case 4 -> enemies.add(new Enemy(465, 332, stage));

                    }
                }

            }
            case 2 -> {

                for (int i = 0; i < secondStageEnemies; i++) {

                    switch (i) {
                        case 0 -> enemies.add(new Enemy(278, 31, stage));
                        case 1 -> enemies.add(new Enemy(31, 309, stage));
                        case 2 -> enemies.add(new Enemy(277, 247, stage));
                        case 3 -> enemies.add(new Enemy(402, 155, stage));
                        case 4 -> enemies.add(new Enemy(465, 320, stage));
                        case 5 -> enemies.add(new Enemy(649, 217, stage));
                        case 6 -> enemies.add(new Enemy(850, 31, stage));
                        case 7 -> enemies.add(new Enemy(894, 217, stage));
                    }
                }

            }
            default -> {

               for (int i = 0; i < thirdStageEnemies; i++) {

                    switch (i) {

                        case 0  -> enemies.add(new Enemy(400, 31, stage));
                        case 1  -> enemies.add(new Enemy(159, 279, stage));
                        case 2  -> enemies.add(new Enemy(154, 123, stage));
                        case 3  -> enemies.add(new Enemy(739, 32, stage));
                        case 4  -> enemies.add(new Enemy(588, 155, stage));
                        case 5  -> enemies.add(new Enemy(891, 153, stage));
                        case 6  -> enemies.add(new Enemy(649, 340, stage));
                        case 7  -> enemies.add(new Enemy(463, 154, stage));
                        case 8  -> enemies.add(new Enemy(525, 218, stage));
                        case 9  -> enemies.add(new Enemy(402, 341, stage));
                        case 10 -> enemies.add(new Enemy(837, 279, stage));
                    }
                }

            }
        }
        return enemies;
    }

    private void moveEntities(){
        player.move(stages[currentStage-1].getWalls());
        stages[currentStage-1].moveEnemies(player);
    }

    private void updatePowerUps(){
        if (player.getSpeed() != 1){
            player.decrementSpeedPowerUpTime();
            if (player.getSpeedPowerUpTime() <= 0){
                player.setSpeed(1);
                player.setSpeedPowerUpTime(420);
                itemsBar[1] = null;
            }
        }
        if (player.getBombsLevel() != 1){
            player.decrementStrongPowerUpTime();
            if (player.getStrongPowerUpTime() <= 0){
                player.setBombsLevel(1);
                player.setStrongPowerUpTime(840);
                itemsBar[2] = null;
            }
        }
    }

    private void drawBar(){
        int x = 127;
        for (Image item : itemsBar){
            if (item != null){
                gc.drawImage(item,x,3,22,22);
                x += 30;
            }
        }
        bombsNum.setText(String.valueOf(player.getMaxBombs()- player.getActiveBombs()));
    }

    private void updateBombTimers() {
        List<Bomb> bombsToRemove = new ArrayList<>();
        for (Bomb bomb : player.getBombs()) {
            bomb.decrementTimer();
            if (!bomb.hasExploded() && bomb.getTimer() <= 0) {
                bomb.explode(stages[currentStage-1].getWalls(), powerUps, player.getMaxBombs());
            }
            if (bomb.getExplosionTimer() <= 0 && bomb.getTimer() <= 0) {
                bombsToRemove.add(bomb);
                player.setActiveBombs(player.getActiveBombs() - 1);
            }
        }
        player.getBombs().removeAll(bombsToRemove);
    }

    private void paintEntities(){
        player.draw(gc);
        stages[currentStage-1].drawEntities(gc);
        drawActiveBombs();
        drawActivePowerUps();
        drawBar();
    }

    private void drawActiveBombs(){
        for (Bomb bomb : player.getBombs()){
            bomb.draw(gc);
        }
    }

    private void drawActivePowerUps(){
        for (PowerUp powerUp : powerUps){
            powerUp.draw(gc);
        }
    }

    private void checkEnemiesCollisions(){
        ArrayList<Explosion> explosions = new ArrayList<>();
        for (Bomb bomb : player.getBombs()){
            explosions.addAll(bomb.getExplosions());
        }
        ArrayList<Enemy> enemiesRemove = new ArrayList<>();
        for (Enemy enemy : stages[currentStage-1].getEnemies()){
            for (Explosion explosion : explosions){
                if (enemy.touches(explosion)){
                    enemiesRemove.add(enemy);
                }
            }
        }
        stages[currentStage-1].getEnemies().removeAll(enemiesRemove);
    }

    private void checkPlayerCollisions(){
        ArrayList<Entity> entities = new ArrayList<>(powerUps);
        entities.addAll(stages[currentStage-1].getEnemies());
        entities.add(stages[currentStage-1].getDoor());
        for (Bomb bomb : player.getBombs()){
            entities.addAll(bomb.getExplosions());
        }
        ArrayList<PowerUp> powerUpsRemove = new ArrayList<>();
        for (Entity entity : entities){
            if (player.touches(entity)){
                if (entity instanceof PowerUp powerUp){
                    PowerUpType type = powerUp.getType();
                    switch (type){
                        case BOMB -> {
                            player.setMaxBombs(player.getMaxBombs() + 1);
                        }
                        case SPEED -> {
                            player.setSpeed(2);
                            itemsBar[1] = powerUp.getSpite();
                        }
                        case STRONG -> {
                            player.setBombsLevel(2);
                            itemsBar[2] = powerUp.getSpite();
                        }
                    }
                    powerUpsRemove.add(powerUp);
                } else if (entity instanceof Door){
                    if (currentStage == 3 && stages[2].getDoor().isOpen()){
                        System.out.println("you win");
                        gameOver = true;
                    } else {
                        switchStage();
                    }
                } else {
                    player.setLives(player.getLives()-1);
                    if (player.getLives() > 0){
                        player.setX(31);
                        player.setY(31);
                    } else {
                        System.out.println("game over");
                        gameOver = true;
                    }
                }
            }
        }
        powerUps.removeAll(powerUpsRemove);
    }

    private void switchStage(){
        powerUps.clear();
        player.setX(31);
        player.setY(31);
        if (currentStage == 1){
            currentStage = 2;
        } else if (currentStage == 2){
            currentStage = 3;
        }
    }

    private ArrayList<Wall> createDestructibleWalls(int stage){
        ArrayList<Wall> walls = new ArrayList<>();
        int[] availableSpaces = switch (stage) {
            case 1 -> new int[]{3,4,5,6,7,8,16,17,18,19,20,
                    32,34,36,40,42,46,48,52,56,
                    59,60,61,62,63,64,65,66,68,69,70,71,72,74,75,76,77,78,80,81,82,84,85,86,
                    88,90,92,94,98,100,104,106,110,114,
                    126,127,128,138,139,140,142,143,
                    144,148,152,156,160,164,168,172,
                    176,177,178,180,181,182,192,193,194,
                    206,210,214,216,220,222,226,228,230,232,
                    234,235,236,238,239,240,242,243,244,245,246,248,249,250,251,252,254,255,256,257,258,259,260,261,
                    264,268,272,274,278,280,284,286,288,
                    300,301,302,303,304,312,313,314,315,316,317
            };
            case 2 -> new int[]{3,4,6,12,13,14,20,21,21,22,
                    32,38,40,42,46,50,54,56,
                    59,60,61,62,64,66,67,68,70,71,72,74,75,76,78,79,80,
                    88,90,96,100,104,108,112,114,116,
                    124,125,126,132,133,134,140,141,142,143,144,145,
                    146,148,150,152,154,158,162,168,170,172,174,
                    175,176,177,178,179,180,186,187,188,198,199,200,
                    204,206,208,216,218,220,224,228,232,
                    244,245,246,248,249,250,252,253,254,260,261,
                    264,266,270,274,278,282,284,288,
                    298,299,300,306,307,308,310,311,312,313,314,316,317
            };
            default -> new int[]{6,7, 8, 9, 10,15,16,17,18,19,20,25,26,27,28,29,
                    36,38, 44 , 46,48,54,55,56,58,
                    64, 65,66,67,68,70,72,73,74,75, 76,77,78,83,84,85,86,87,
                    94,96,102, 104, 106, 112,114, 116,
                    117,118, 119,120,121,122,123,124,125,126,132,133,134,
                    146,148,150,152,154, 156, 160,162, 164, 168,170, 172, 174,
                    175,176, 177, 178,179,180, 181, 182, 183,184, 185, 186,188,189,190, 192, 193, 194, 196, 197, 198,199,200, 201,202,203,
                    204,206, 208,210,212, 214,218, 222, 226, 228, 230, 232,
                    233, 234,235, 236, 242, 243, 244, 246, 247, 248, 250, 251, 252, 254, 255, 256, 257, 258,
                    262,264, 268, 272,276, 280, 284, 286,
                    291,292,293,294,296, 297, 298, 312, 313, 314, 315, 316
            };
        };
        int x = 30, y = 30;
        for (int i = 1; i < 320; i++) {
            boolean createWall = false;
            for (int availableSpace : availableSpaces) {
                if (i == availableSpace) {
                    createWall = true;
                    break;
                }
            }
            if (createWall) {
                DestructibleWall destructibleWall = new DestructibleWall(x, y, 32, 32,stage);
                walls.add(destructibleWall);
            }
            x += 31;
            if (i % 29 == 0) {
                y += 31;
                x = 30;
            }
        }
        return walls;
    }

    private ArrayList<Wall> createStaticWalls(){
        ArrayList<Wall> walls = new ArrayList<>();
        //borde superior
        int x = 31;
        for (int i = 0; i < 29; i++){
            Wall wall = new Wall(x,29,32,1);
            walls.add(wall);
            x += 31;
        }
        //borde inferior
        x=31;
        for (int i = 0; i < 29; i++){
            Wall wall = new Wall(x,371,32,1);
            walls.add(wall);
            x += 31;
        }
        //borde izquierdo
        int y = 31;
        for (int i = 0; i < 11; i++){
            Wall wall = new Wall(29,y,1,32);
            walls.add(wall);
            y += 31;
        }
        //borde derecho
        y = 31;
        for (int i = 0; i < 11; i++){
            Wall wall = new Wall(929,y,1,32);
            walls.add(wall);
            y += 31;
        }
        x = 61;
        y = 61;
        int h = 32, w = 32;
        for (int i = 0; i < 14; i++) {
            Wall wall = new Wall(x,y,w,h);
            walls.add(wall);
            x += 62;
        }
        x = 61;
        y = 123;
        for (int i = 0; i < 14; i++) {
            Wall wall = new Wall(x,y,w,h);
            walls.add(wall);
            x += 62;
        }
        x = 61;
        y = 185;
        for (int i = 0; i < 14; i++) {
            Wall wall = new Wall(x,y,w,h);
            walls.add(wall);
            x += 62;
        }
        x = 61;
        y = 247;
        for (int i = 0; i < 14; i++) {
            Wall wall = new Wall(x,y,w,h);
            walls.add(wall);
            x += 62;
        }
        x = 61;
        y = 309;
        for (int i = 0; i < 14; i++) {
            Wall wall = new Wall(x,y,w,h);
            walls.add(wall);
            x += 62;
        }
        return walls;
    }
}