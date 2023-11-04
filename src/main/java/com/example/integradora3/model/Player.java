package com.example.integradora3.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Player extends Thread {

    private int x;
    private int y;
    private int width;
    private int height;
    private int lives;
    private boolean move;
    private Circle bomb;

    private

    GraphicsContext gc;
    public Player() {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.move = move;
        this.lives = 3;
        this.bomb = bomb;
    }
    /**
    @Override
    public void run() {

        super.run();
        /*
        while (true) {

            gc.setFill(Color.FUCHSIA);

            gc.fillOval(obstacle.getCenterX()-obstacle.getRadius(), obstacle.getCenterY()-obstacle.getRadius(), obstacle.getRadius()*2,obstacle.getRadius()*2);

            gc.setFill(playerColor);
            gc.fillRect(x, y, width, height);


            if(playerRectangle.intersects(obstacle.getBoundsInParent())) {
                System.out.println("collision!");
            } else {
                System.out.println("no collision");
            }

            try {
                sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            gc.clearRect(x, y, width, height);
            if (move){
                x+=5;
                playerRectangle.setX(x);
            }
        }

    }*/

    public boolean isMove() {
        return move;
    }

    public void setMove(boolean move) {
        this.move = move;
    }
}

