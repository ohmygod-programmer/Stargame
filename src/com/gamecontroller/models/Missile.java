package com.gamecontroller.models;

import com.gamecontroller.Entity;
import com.gamecontroller.GameAction;

import static java.lang.Math.*;

public class Missile extends Entity {
    public static final int LASER = 1;
    public static final int BIG_LASER = 2;
    int type = LASER;
    double damage = 10;

    public int getType() {
        return type;
    }

    public double getDamage() {
        return damage;
    }

    public Missile(int type, double damage) {
        this.type = type;
        this.damage = damage;
        switch (type) {
            case (LASER):
                speed = 5;
                break;
            case (BIG_LASER):
                speed = 4;
                break;
        }
    }

    @Override
    public void startbehavior() {
        GameAction move = new GameAction(GameAction.MOVE);
        move.putArgs(-speed * sin(toRadians(direction)), -speed * cos(toRadians(direction)));
        actions.add(move);
    }

}
