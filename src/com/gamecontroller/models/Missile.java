package com.gamecontroller.models;

import com.gamecontroller.GameAction;
import com.gamecontroller.GameModel;

import static java.lang.Math.*;

public class Missile extends GameModel {
    public static final int LASER = 1;
    int type = LASER;
    double damage = 10;

    public Missile(int type, double damage) {
        this.type = type;
        this.damage = damage;
        if (type == LASER) {
            speed = 10;
        }
    }

    @Override
    public void startbehavior() {
        GameAction move = new GameAction(GameAction.MOVE);
        move.putArgs(speed * cos(toRadians(direction)), speed * sin(toRadians(direction)));
        actions.add(move);
    }

}
