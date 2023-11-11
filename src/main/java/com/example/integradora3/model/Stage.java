package com.example.integradora3.model;

import java.util.ArrayList;

public class Stage {

    private ArrayList<Wall> walls;
    private ArrayList<Enemy> enemies;
    private Door[] doors;
    private int id;

    public Stage(ArrayList<Wall> walls, ArrayList<Enemy> enemies, Door[] doors, int id) {
        this.walls = new ArrayList<>(walls);
        this.enemies = new ArrayList<>(enemies);
        this.doors = doors;
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public ArrayList<Enemy> getEnemies(){
        return enemies;
    }

    public ArrayList<Wall> getWalls(){
        return walls;
    }

}
