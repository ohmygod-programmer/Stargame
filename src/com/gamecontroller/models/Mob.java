package com.gamecontroller.models;

import com.gamecontroller.GameModel;

public abstract class Mob extends GameModel {

    double health = 1;

    double getHealth() {
        return health;
    }

    void damage(int dmg) {
        health -= dmg;
    }
}

class TIE_Figther extends Mob {

    TIE_Figther(){
        speed = 2;
    }

    @Override
    public void startbehavior() {

    }
}