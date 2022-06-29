package com.gamecontroller.models;

import com.gamecontroller.GameAction;

import java.util.ArrayList;

public class Monster extends Mob {

    public Monster(){
        health = 50;
        speed = 1;
        radius = 35;
        mobtype = Mob.Monster;
    }

    @Override
    public void startbehavior() {
        if (tick_after_spawn % 6 == 0) {
            GameAction move = new GameAction(GameAction.MOVE);
            double x = speed * (-Math.sin(Math.toRadians(direction)));
            double y = speed * (-Math.cos(Math.toRadians(direction)));
            move.putArgs(x, y);
            actions.add(move);
        }
        if (tick_after_spawn % 120 == 0){
            GameAction shoot = new GameAction(GameAction.SHOOT);
            shoot.putArgs(Missile.BIG_LASER, 20);
            actions.add(shoot);
        }
    }

    @Override
    public void calculate_death(ArrayList<Mob> a, int mob_id){
        a.remove(mob_id);
    }
}