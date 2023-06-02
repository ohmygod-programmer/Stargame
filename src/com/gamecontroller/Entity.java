package com.gamecontroller;

import java.util.LinkedList;

public abstract class Entity {
    protected int tick_after_spawn = 1;
    double x_cord = 0;
    double y_cord = 0;
    protected double direction = 0; //in degrees
    public double radius = 0;
    public int speed = 0;

    public void move(double newx, double newy) {
        x_cord = newx;
        y_cord = newy;
    }

    public double getX_cord() {
        return x_cord;
    }
    public void setX_cord(double x) {
        x_cord = x;
    }
    public double getY_cord() {
        return y_cord;
    }
    public void setY_cord(double y) {
        y_cord = y;
    }
    public double getDirection() {
        return direction;
    }
    public void setDirection(double direction) {
        this.direction = direction;
    }
    public int getTick_after_spawn() {
        return tick_after_spawn;
    }
    public void plus_Tick(){
        tick_after_spawn++;
    }

    public LinkedList<GameAction> actions = new LinkedList<GameAction>();

    abstract public void startbehavior();

    public boolean ishaveactions() {
        return !actions.isEmpty();
    }

    public GameAction popaction() {
        return actions.pop();
    }
    protected Entity(){};

}

