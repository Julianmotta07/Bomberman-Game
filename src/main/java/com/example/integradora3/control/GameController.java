package com.example.integradora3.control;

import com.example.integradora3.KeyboardControl;
import com.example.integradora3.model.DestructibleWall;
import com.example.integradora3.model.Obstacle;
import com.example.integradora3.model.ObstaclesThread;
import com.example.integradora3.model.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameController extends Thread implements Initializable {

    @FXML
    private Canvas canvas;
    private GraphicsContext gc;
    private Player player;
    private ArrayList<Obstacle> obstacles;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        obstacles = new ArrayList<>();
        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(KeyboardControl::onKeyPressed);
        canvas.setOnKeyReleased(KeyboardControl::onKeyReleased);
        createObstacles();
        player = new Player(gc,obstacles);
        //player.start();
        //ObstaclesThread obstaclesThread = new ObstaclesThread(obstacles, gc);
        //obstaclesThread.start();
        //drawObstacles();
        this.start();
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            player.move();
            player.draw();
            drawObstacles();
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void drawObstacles(){
        for (Obstacle obstacle : obstacles){
            if (obstacle instanceof DestructibleWall dw){
                dw.draw();
            }
        }
    }

    public void createObstacles(){
        createStaticWalls();
        createDestructibleWalls();
    }

    public void createDestructibleWalls(){
        int x = 30, y = 29;
        for (int i = 1; i < 320; i++) {
            boolean createWall = false;
            for (int availableSpace : availableSpaces) {
                if (i == availableSpace) {
                    createWall = true;
                    break;
                }
            }
            if (createWall) {
                DestructibleWall destructibleWall = new DestructibleWall(x, y, 32, 32, gc);
                obstacles.add(destructibleWall);
            }
            x += 31;
            if (i % 29 == 0) {
                y += 31;
                x = 30;
            }
        }
    }

    private int availableSpaces[] = {3,4,5,6,7,8,22,23,24,
                                    32,34,36,40,42,44,46,48,52,56,
                                    59,60,61,62,63,64,65,66,68,69,70,71,72,73,74,75,76,77,78,80,81,82,84,85,86,
                                    88,90,92,94,98,100,102,104,106,110,114,
                                    122,123,124,126,127,128,134,135,136,138,139,140,142,143,
                                    144,148,152,156,160,164,168,172,
                                    176,177,178,180,181,182,184,185,186,192,193,194,196,197,198,
                                    206,210,214,216,218,220,222,226,228,230,232,
                                    234,235,236,238,239,240,242,243,244,245,246,247,248,249,250,251,252,254,255,256,257,258,259,260,261,
                                    264,268,272,274,276,278,280,284,286,288,
                                    296,297,298,312,313,314,315,316,317};

    private void createStaticWalls(){
        Obstacle topEdge = new Obstacle(0,0,1010,21);
        obstacles.add(topEdge);
        Obstacle bottomEdge = new Obstacle(0,371,1010,30);
        obstacles.add(bottomEdge);
        Obstacle rightEdge = new Obstacle(931,32,32,362);
        obstacles.add(rightEdge);
        Obstacle leftEdge = new Obstacle(0,32,32,362);
        obstacles.add(leftEdge);
        int h = 32, w = 32, x = 61, y = 61;
        for (int i = 0; i < 14; i++) {
            Obstacle obstacle = new Obstacle(x,y,w,h);
            obstacles.add(obstacle);
            x += 62;
        }
        x = 61;
        y = 123;
        for (int i = 0; i < 14; i++) {
            Obstacle obstacle = new Obstacle(x,y,w,h);
            obstacles.add(obstacle);
            x += 62;
        }
        x = 61;
        y = 185;
        for (int i = 0; i < 14; i++) {
            Obstacle obstacle = new Obstacle(x,y,w,h);
            obstacles.add(obstacle);
            x += 62;
        }
        x = 61;
        y = 247;
        for (int i = 0; i < 14; i++) {
            Obstacle obstacle = new Obstacle(x,y,w,h);
            obstacles.add(obstacle);
            x += 62;
        }
        x = 61;
        y = 309;
        for (int i = 0; i < 14; i++) {
            Obstacle obstacle = new Obstacle(x,y,w,h);
            obstacles.add(obstacle);
            x += 62;
        }
    }
}
