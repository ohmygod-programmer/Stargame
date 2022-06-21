package com.gamecontroller.models;

import com.gamecontroller.GameAction;
import com.gamecontroller.GameModel;
import com.graphics.GraphicsModule;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player extends Mob {

    GraphicsModule controller;

    public Player(GraphicsModule c) {
        controller = c;
        health = 100;
        speed = 5;
        radius = 45;
    }

    @Override
    public void startbehavior() {
        GameAction move = new GameAction(GameAction.MOVE);
        int x = 0;
        int y = 0;
        if (controller.getkeystatus(KeyEvent.VK_RIGHT)) {
            x += speed;
        }
        if (controller.getkeystatus(KeyEvent.VK_LEFT)) {
            x -= speed;
        }
        if (controller.getkeystatus(KeyEvent.VK_UP)) {
            y -= speed;
        }
        if (controller.getkeystatus(KeyEvent.VK_DOWN)) {
            y += speed;
        }
        move.putArgs(x, y);
        actions.add(move);
        if (getTick_after_spawn() % 50 == 0){
            GameAction shoot = new GameAction(GameAction.SHOOT);
            shoot.putArgs(Missile.LASER, 10);
            actions.add(shoot);
        }

    }

}