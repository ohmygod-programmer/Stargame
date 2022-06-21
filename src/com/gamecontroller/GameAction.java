package com.gamecontroller;

import java.util.ArrayList;
import java.util.Collection;

public class GameAction {

    public static final int MOVE = 1; //1st arg - X; 2nd - Y;
    public static final int MOVE_X_ARG = 0;
    public static final int MOVE_Y_ARG = 1;
    public static final int SHOOT = 5;
    public static final int SHOOT_MISSILE_TYPE_ARG = 0;
    public static final int SHOOT_MISSILE_DAMAGE_ARG = 1;

    public int id = -1;
    ArrayList args = new ArrayList();

    public GameAction(int num) {
        id = num;
    }

    public void putArgs(Object... a) {
        args.clear();
        for (int i = 0; i < a.length; i++){
            args.add(a[i]);
        }
    }
    public ArrayList getArgs() {
        return args;
    }

}
