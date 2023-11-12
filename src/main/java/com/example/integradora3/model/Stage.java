package com.example.integradora3.model;

import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;

public class Stage {

    private ArrayList<Wall> walls;
    private ArrayList<Enemy> enemies;
    private Door[] doors;

    public Stage(ArrayList<Wall> walls,ArrayList<Enemy> enemies, Door[] doors) {
        this.walls = new ArrayList<>(walls);
        this.enemies = new ArrayList<>(enemies);
        this.doors = doors;
    }

    public void drawEntities(GraphicsContext gc){
        for (Wall wall : walls){
            if (wall instanceof DestructibleWall dw){
                dw.draw(gc);
            }
        }
        for (Enemy enemy : enemies){
            enemy.draw(gc);
        }
        doors[0].draw(gc);
        if (doors.length > 1){
            doors[1].draw(gc);
        }
    }

    public void moveEnemies(Player player){
        for (Enemy enemy : enemies){
            enemy.moveTowardsPlayer(player,walls);
        }
    }

    public ArrayList<Enemy> getEnemies(){
        return enemies;
    }

    public ArrayList<Wall> getWalls(){
        return walls;
    }

}
