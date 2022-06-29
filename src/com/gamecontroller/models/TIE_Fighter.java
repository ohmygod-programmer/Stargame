package com.gamecontroller.models;

import com.gamecontroller.GameAction;

import java.util.ArrayList;

public class TIE_Fighter extends Mob {

    public TIE_Fighter(){
        speed = 1;
        radius = 20;
        mobtype = Mob.TIE_Fighter;
    }

    @Override
    public void startbehavior() {
        if (tick_after_spawn % 4 == 0) {
            GameAction move = new GameAction(GameAction.MOVE);
            double x = speed * (-Math.sin(Math.toRadians(direction)));
            double y = speed * (-Math.cos(Math.toRadians(direction)));
            move.putArgs(x, y);
            actions.add(move);
        }
        if (tick_after_spawn % 75 == 0){
            GameAction shoot = new GameAction(GameAction.SHOOT);
            shoot.putArgs(Missile.LASER, 10);
            actions.add(shoot);
        }
    }

    @Override
    public void calculate_death(ArrayList<Mob> a, int mob_id){
        a.remove(mob_id);
    }
}