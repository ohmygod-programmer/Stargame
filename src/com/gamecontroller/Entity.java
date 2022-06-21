package com.gamecontroller;

import com.ViewModel;

public class Entity {
    public static final int Player = 0;
    public static final int Missile = 5;
    public ViewModel view;
    public GameModel core;
    int gamemodeltype=0;
    public Entity(){};
    public Entity(GameModel g, ViewModel v, int type) {
        core = g;
        view = v;
        gamemodeltype = type;
    }
    public Entity(GameModel model, int type) {
        core = model;
        view = new ViewModel();
        gamemodeltype = type;
    }
}

