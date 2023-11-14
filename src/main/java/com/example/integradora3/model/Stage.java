package com.example.integradora3.model;

import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;

public class Stage {

    private ArrayList<Wall> walls;
    private ArrayList<Enemy> enemies;
    private Door door;

    public Stage(ArrayList<Wall> walls,ArrayList<Enemy> enemies, Door door) {
        this.walls = new ArrayList<>(walls);
        this.enemies = new ArrayList<>(enemies);
        this.door = door;
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
        if (enemies.isEmpty() && !door.isOpen()){
            door.setOpen(true);
        }
        door.draw(gc);
    }

    public void moveEnemies(Player player) {
        ArrayList<Entity> entities = new ArrayList<>(walls);
        for (Enemy enemy : enemies) {
            ArrayList<Enemy> otherEnemies = new ArrayList<>(enemies);
            otherEnemies.remove(enemy);
            entities.addAll(otherEnemies);
            enemy.moveTowardsPlayer(player, entities);
        }
    }

    public ArrayList<Enemy> getEnemies(){
        return enemies;
    }

    public ArrayList<Wall> getWalls(){
        return walls;
    }

    public Door getDoor(){
        return door;
    }

}
