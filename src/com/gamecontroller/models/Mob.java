package com.gamecontroller.models;

import com.gamecontroller.Entity;

import java.util.ArrayList;

public abstract class Mob extends Entity {
    public static final int Player = 0;
    public static final int TIE_Fighter = 1;
    public static final int Monster = 2;
    int mobtype = 0;
    double health = 1;

    public void setMobtype(int mobtype) {
        this.mobtype = mobtype;
    }

    public int getMobtype() {
        return mobtype;
    }

    public double getHealth() {
        return health;
    }

    public void damage(double dmg) {
        health -= dmg;
    }

    public abstract void calculate_death(ArrayList<Mob> a, int mob_id);
}

