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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(KeyboardControl::onKeyPressed);
        canvas.setOnKeyReleased(KeyboardControl::onKeyReleased);
        initializeGame();
        Thread gameThread = new Thread(() -> {
            while (true) {
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
        currentStage = 1;
        powerUps = new ArrayList<>();
        player = new Player();
        bombsNum.setText("1");
        itemsBar = new Image[3];
        itemsBar[0] = new Image("file:" + MainMenu.getFile("images/bomb_icon.png").getPath());
        createStages();
    }

    private void createStages(){
        stages = new Stage[3];
        stages[0] = new Stage(createWalls(1),createEnemies(1),createDoors(1));
        stages[1] = new Stage(createWalls(2),createEnemies(2),createDoors(2));
        stages[2] = new Stage(createWalls(3),createEnemies(3),createDoors(3));
    }

    private ArrayList<Wall> createWalls(int stage){
        ArrayList<Wall> walls = new ArrayList<>(createStaticWalls());
        walls.addAll(createDestructibleWalls(stage));
        return walls;
    }

    private ArrayList<Enemy> createEnemies(int stage){
        ArrayList<Enemy> enemies = new ArrayList<>();
        switch (stage) {
            case 1 -> {
                enemies.add(new Enemy(400, 31, stage));
                enemies.add(new Enemy(31, 340, stage));
            }
            case 2 -> enemies.add(new Enemy(400, 31, stage));
            default -> enemies.add(new Enemy(400, 31, stage));
        }
        return enemies;
    }

    private Door[] createDoors(int stage){
        Door[] doors = new Door[stage == 2 ? 2 : 1];
        switch (stage) {
            case 1 -> {
                doors[0]= new Door(898,340);
            }
            case 2 -> {
                doors[0]= new Door(33,33);
                doors[1]= new Door(898,340);
            }
            case 3 -> {
                doors[0]= new Door(33,33);
            }
        }
        return doors;
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
                } else {
                    player.setLives(player.getLives()-1);
                    if (player.getLives() > 0){
                        player.setX(32);
                        player.setY(32);
                    } else {
                        System.out.println("game over");
                    }
                }
            }
        }
        powerUps.removeAll(powerUpsRemove);
    }

    private ArrayList<Wall> createDestructibleWalls(int stage){
        ArrayList<Wall> walls = new ArrayList<>();
        int[] availableSpaces = switch (stage) {
            case 1 -> new int[]{34, 36, 40, 42, 44, 46, 48, 52, 56,
                    62, 63, 64, 65, 66, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 80, 81, 82, 84, 85, 86,
                    88, 90, 92, 94, 98, 100, 102, 104, 106, 110, 114,
                    122, 123, 124, 126, 127, 128, 134, 135, 136, 138, 139, 140, 142, 143,
                    144, 148, 152, 156, 160, 164, 168, 172,
                    176, 177, 178, 180, 181, 182, 184, 185, 186, 192, 193, 194, 196, 197, 198,
                    206, 210, 214, 216, 218, 220, 222, 226, 228, 230, 232,
                    234, 235, 236, 238, 239, 240, 242, 243, 244, 245, 246, 247, 248, 249, 250, 251, 252, 254, 255, 256, 257, 258, 259, 260, 261,
                    264, 268, 272, 274, 276, 278, 280, 284, 286, 288,
                    296, 297, 298, 312, 313, 314, 315, 316, 317
            };
            //delete 34
            case 2 -> new int[]{36, 40, 42, 44, 46, 48, 52, 56,
                    62, 63, 64, 65, 66, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 80, 81, 82, 84, 85, 86,
                    88, 90, 92, 94, 98, 100, 102, 104, 106, 110, 114,
                    122, 123, 124, 126, 127, 128, 134, 135, 136, 138, 139, 140, 142, 143,
                    144, 148, 152, 156, 160, 164, 168, 172,
                    176, 177, 178, 180, 181, 182, 184, 185, 186, 192, 193, 194, 196, 197, 198,
                    206, 210, 214, 216, 218, 220, 222, 226, 228, 230, 232,
                    234, 235, 236, 238, 239, 240, 242, 243, 244, 245, 246, 247, 248, 249, 250, 251, 252, 254, 255, 256, 257, 258, 259, 260, 261,
                    264, 268, 272, 274, 276, 278, 280, 284, 286, 288,
                    296, 297, 298, 312, 313, 314, 315, 316, 317
            };
            //delete 36
            default -> new int[]{34, 40, 42, 44, 46, 48, 52, 56,
                    62, 63, 64, 65, 66, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 80, 81, 82, 84, 85, 86,
                    88, 90, 92, 94, 98, 100, 102, 104, 106, 110, 114,
                    122, 123, 124, 126, 127, 128, 134, 135, 136, 138, 139, 140, 142, 143,
                    144, 148, 152, 156, 160, 164, 168, 172,
                    176, 177, 178, 180, 181, 182, 184, 185, 186, 192, 193, 194, 196, 197, 198,
                    206, 210, 214, 216, 218, 220, 222, 226, 228, 230, 232,
                    234, 235, 236, 238, 239, 240, 242, 243, 244, 245, 246, 247, 248, 249, 250, 251, 252, 254, 255, 256, 257, 258, 259, 260, 261,
                    264, 268, 272, 274, 276, 278, 280, 284, 286, 288,
                    296, 297, 298, 312, 313, 314, 315, 316, 317
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
                DestructibleWall destructibleWall = new DestructibleWall(x, y, 32, 32);
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