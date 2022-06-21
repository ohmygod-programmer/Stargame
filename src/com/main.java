package com;

import com.gamecontroller.Entity;
import com.gamecontroller.GameAction;
import com.gamecontroller.models.Missile;
import com.gamecontroller.models.Player;
import com.graphics.GraphicsModule;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class main {
    static final int XWINDOWSIZE = 800;
    static final int YWINDOWSIZE = 600;
    static final int MAXFPS = 60;

    private static ArrayList<Entity> missiles = new ArrayList<Entity>();
    private static ArrayList<Entity> mobs = new ArrayList<Entity>();
    private static ArrayList<ViewModel> models = new ArrayList<ViewModel>();

    private static ViewModel laser_view = new ViewModel();

    private static void calculate_behavior(Entity ent){
        ent.core.startbehavior();
        while (ent.core.ishaveactions()){
            GameAction action = ent.core.popaction();
            if(action.id==GameAction.MOVE){
                ArrayList a = action.getArgs();
                int x = (int)a.get(GameAction.MOVE_X_ARG);
                int y = (int)a.get(GameAction.MOVE_Y_ARG);
                if (ent.core.getX_cord() + ent.core.radius + x <XWINDOWSIZE &&
                        ent.core.getX_cord() - ent.core.radius +x > 0){
                    ent.core.setX_cord(ent.core.getX_cord()+x);
                }
                if (ent.core.getY_cord() + ent.core.radius + y <YWINDOWSIZE &&
                        ent.core.getY_cord() - ent.core.radius +y > 0){
                    ent.core.setY_cord(ent.core.getY_cord()+y);
                }

            }
            /*if(action.id==GameAction.SHOOT) {
                ArrayList a = action.getArgs();
                int type = (int) a.get(GameAction.SHOOT_MISSILE_TYPE_ARG);
                int damage = (int) a.get(GameAction.SHOOT_MISSILE_DAMAGE_ARG);
                Entity missile = new Entity(new Missile(type, damage), new ViewModel(laser_view), Entity.Missile);
            }*/
        }
    }

    public static void main(String[] args) {


        Image laser_img = new ImageIcon("src/laser.png").getImage();
        laser_view.setImage(laser_img);
        laser_view.setHeight(50);
        laser_view.setHeight(50);

        GraphicsModule graphicsModule = new GraphicsModule();
        int maxworktime = 1000/MAXFPS;

        Entity player = new Entity(new Player(graphicsModule), Entity.Player);
        Image player_img = new ImageIcon("src/spaceship.png").getImage();
        player.view.setImage(player_img);
        player.view.setHeight(96);
        player.view.setWidth(96);
        player.core.move(XWINDOWSIZE * 0.5, YWINDOWSIZE * 0.8);





        mobs.add(player);


        graphicsModule.startmainframe();

        while (true) {
            long start = System.currentTimeMillis();
            for (int i = 0; i < mobs.size(); i++){
                calculate_behavior(mobs.get(i));
            }
            for (int i = 0; i < mobs.size(); i++){
                Entity curEntity = mobs.get(i);
                ViewModel model = new ViewModel();
                curEntity.view.setX_cord(curEntity.core.getX_cord()-curEntity.view.width/2);
                curEntity.view.setY_cord(curEntity.core.getY_cord()-curEntity.view.height/2);
                models.add(curEntity.view);
            }

            graphicsModule.putmodels(models);
            graphicsModule.refreshframe();

            long cur_time = System.currentTimeMillis();
            long worktime = cur_time - start;
            if (worktime < maxworktime){
                try {
                    sleep(maxworktime - worktime);
                }
                catch (Exception e){

                }
            }

            models.clear();
        }
    }
}
