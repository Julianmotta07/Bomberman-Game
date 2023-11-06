package com.example.integradora3;

import javafx.scene.input.KeyEvent;

public class KeyboardControl {

    public static boolean upPressed;
    public static boolean downPressed;
    public static boolean leftPressed;
    public static boolean rightPressed;

    public static void onKeyPressed(KeyEvent key) {
        switch (key.getCode()) {
            case UP:
                upPressed = true;
                break;
            case DOWN:
                downPressed = true;
                break;
            case LEFT:
                leftPressed = true;
                break;
            case RIGHT:
                rightPressed = true;
                break;
            case SPACE:
                break;
            default:
                break;
        }
    }

    public static void onKeyReleased(KeyEvent key) {
        switch (key.getCode()) {
            case UP:
                upPressed = false;
                break;
            case DOWN:
                downPressed = false;
                break;
            case LEFT:
                leftPressed = false;
                break;
            case RIGHT:
                rightPressed = false;
                break;
            case SPACE:
                break;
            default:
                break;
        }
    }
}
