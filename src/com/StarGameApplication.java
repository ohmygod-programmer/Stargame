package com;

import com.gamecontroller.Entity;
import com.gamecontroller.GameAction;
import com.gamecontroller.models.*;
import com.graphics.GraphicsModule;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class StarGameApplication {
    static final public int XWINDOWSIZE = 800;
    static final public int YWINDOWSIZE = 600;
    static final public int MAXFPS = 60;

    private static long tick_main = 0;

    private static ArrayList<Missile> missiles = new ArrayList<Missile>();
    private static ArrayList<Mob> mobs = new ArrayList<>();
    private static ArrayList<ViewModel> models = new ArrayList<ViewModel>();

    private static ViewModel view_of_Laser = new ViewModel();
    private static ViewModel view_of_Big_Laser = new ViewModel();
    private static ViewModel view_of_Player = new ViewModel();
    private static ViewModel view_of_TIE_Fighter = new ViewModel();
    private static ViewModel view_of_Monster = new ViewModel();

    private static boolean calculate_missile_hit(int missile_id, int mob_id){
        Missile missile = missiles.get(missile_id);
        Mob mob = mobs.get(mob_id);
        boolean isremoved = false;
        switch (missile.getType()){
            case (Missile.LASER):
                mob.damage(missile.getDamage());
                missiles.remove(missile_id);
                isremoved = true;
                break;
            case (Missile.BIG_LASER):
                mob.damage(missile.getDamage());
                missiles.remove(missile_id);
                isremoved = true;
                break;
        }

        return isremoved;
    }

    private static void calculate_mobs(){
        for (int i = 0; i<mobs.size(); i++){
            Mob mob = mobs.get(i);
            if (mob.getHealth() <= 0){
                mob.calculate_death(mobs, i);
                i--;
                continue;
            }
            mob.startbehavior();
            mob.plus_Tick();
            while (mob.ishaveactions()){
                GameAction action = mob.popaction();
                if(action.id==GameAction.MOVE){
                    ArrayList a = action.getArgs();
                    double x = (double)a.get(GameAction.MOVE_X_ARG);
                    double y = (double)a.get(GameAction.MOVE_Y_ARG);
                    if ((x > 0) && (mob.getX_cord() + mob.radius + x <XWINDOWSIZE) ||
                            (mob.getX_cord() - mob.radius +x > 0) && (x<0)){
                        mob.setX_cord(mob.getX_cord()+x);
                    }
                    if ((y > 0) && (mob.getY_cord() + mob.radius + y <YWINDOWSIZE) ||
                            (mob.getY_cord() - mob.radius +y > 0) && (y<0)){
                        mob.setY_cord(mob.getY_cord()+y);
                    }

                }
                if(action.id==GameAction.SHOOT) {
                    ArrayList a = action.getArgs();
                    int type = (int) a.get(GameAction.SHOOT_MISSILE_TYPE_ARG);
                    int damage = (int) a.get(GameAction.SHOOT_MISSILE_DAMAGE_ARG);
                    Missile missile = new Missile(type, damage);

                    missile.move(mob.getX_cord()-(mob.radius+1)*Math.sin(Math.toRadians(mob.getDirection())),
                            mob.getY_cord()-(mob.radius+1)*Math.cos(Math.toRadians(mob.getDirection())));
                    missile.setDirection(mob.getDirection());
                    missiles.add(missile);
                }
            }
        }

    }
    private static void calculate_missiles(){
        for (int i = 0; i<missiles.size(); i++){
            boolean isremoved = false;
            Entity ent = missiles.get(i);
            ent.startbehavior();
            ent.plus_Tick();

            while (ent.ishaveactions()){
                GameAction action = ent.popaction();
                if(action.id==GameAction.MOVE){
                    ArrayList a = action.getArgs();
                    double x = (double)a.get(GameAction.MOVE_X_ARG);
                    double y = (double)a.get(GameAction.MOVE_Y_ARG);
                    if ((x >= 0) && (ent.getX_cord() + ent.radius + x <XWINDOWSIZE) ||
                            (ent.getX_cord() - ent.radius +x > 0) && (x<=0)){
                        ent.setX_cord(ent.getX_cord()+x);
                    }
                    else if (ent.getClass() == Missile.class){
                        missiles.remove(i);
                        isremoved = true;
                        break;
                    }
                    if ((y >= 0) && (ent.getY_cord() + ent.radius + y <YWINDOWSIZE) ||
                            (ent.getY_cord() - ent.radius +y > 0) && (y<=0)){
                        ent.setY_cord(ent.getY_cord()+y);
                    }
                    else if (ent.getClass() == Missile.class){
                        missiles.remove(i);
                        isremoved = true;
                        break;
                    }

                }
            }
            if (isremoved){
                i--;
                continue;
            }
            for (int mob = 0; mob < mobs.size(); mob++){
                Mob curMob = mobs.get(mob);
                double xdistance = curMob.getX_cord() - ent.getX_cord();
                double ydistance = curMob.getY_cord() - ent.getY_cord();
                if (Math.sqrt(xdistance*xdistance+ydistance*ydistance)<(curMob.radius + ent.radius)){
                    isremoved = calculate_missile_hit(i, mob);
                }
                if (isremoved){
                    break;
                }
            }
        }

    }

    static BufferedImage errorimg = null;
    private static BufferedImage tryreadimg(String s){
        BufferedImage img;
        try {
            img= ImageIO.read(StarGameApplication.class.getResource(s));//new ImageIcon("src/laser.png").getImage();

        }
        catch (Exception e){
            img = errorimg;
        }
        return img;
    }

    public static long getTick_main() {
        return tick_main;
    }

    public static void main(String[] args) {
        try {
            errorimg = ImageIO.read(new File("/Error.jpg"));
        }
        catch (Exception e){}

        BufferedImage img_of_Laser = tryreadimg("/Laser.png");
        view_of_Laser.setImage(img_of_Laser);
        view_of_Laser.setHeight(20);
        view_of_Laser.setWidth(20);
        view_of_Big_Laser.setImage(img_of_Laser);
        view_of_Big_Laser.setHeight(40);
        view_of_Big_Laser.setWidth(40);
        BufferedImage img_of_Player = tryreadimg("/Main.png");
        view_of_Player.setImage(img_of_Player);
        view_of_Player.setHeight(120);
        view_of_Player.setWidth(120);
        BufferedImage img_of_TIE_Fighter = tryreadimg("/TIE_Fighter.png");
        view_of_TIE_Fighter.setImage(img_of_TIE_Fighter);
        view_of_TIE_Fighter.setHeight(60);
        view_of_TIE_Fighter.setWidth(60);
        BufferedImage img_of_Monster = tryreadimg("/Monster.png");
        view_of_Monster.setImage(img_of_Monster);
        view_of_Monster.setHeight(80);
        view_of_Monster.setWidth(80);

        GraphicsModule graphicsModule = new GraphicsModule(XWINDOWSIZE, YWINDOWSIZE);
        int maxworktime = 1000/MAXFPS;

        Player player = new Player(graphicsModule);
        player.move(XWINDOWSIZE * 0.5, YWINDOWSIZE * 0.8);
        player.setDirection(0);

        mobs.add(player);


        graphicsModule.startmainframe();

        while (true) {
            graphicsModule.clearmodels();
            long start = System.currentTimeMillis();

            if (tick_main % 200 == 0){
                TIE_Fighter tie = new TIE_Fighter();
                double rand = Math.random();
                double x = XWINDOWSIZE * (0.5 + 0.5 * Math.cos(rand*Math.PI));
                double y = YWINDOWSIZE* (0.4 - 0.4 * Math.sin(rand*Math.PI));
                tie.move(x, y);
                double rand1 = Math.random();
                double xcentral = x - XWINDOWSIZE * 0.5;
                double ycentral = YWINDOWSIZE * 0.5 - y;

                double degrees;
                if (x > XWINDOWSIZE * 0.5){
                    degrees = 100 + Math.toDegrees(Math.asin(ycentral/Math.sqrt(xcentral*xcentral+ycentral*ycentral)))
                            + (rand1) * 20;
                }
                else {
                    degrees = 260 - Math.toDegrees(Math.asin(ycentral/Math.sqrt(xcentral*xcentral+ycentral*ycentral)))
                            - (rand1) * 20;
                }

                tie.setDirection(degrees);
                mobs.add(tie);
            }

            if (tick_main % 500 == 0){
                Monster monster = new Monster();
                double rand = Math.random();
                double x = XWINDOWSIZE * (0.1 + rand * 0.8);
                double y = YWINDOWSIZE * 0.1;
                monster.move(x, y);
                monster.setDirection(180);
                mobs.add(monster);
            }

            calculate_missiles();

            for (int i = 0; i < missiles.size(); i++){
                Missile curMissile = missiles.get(i);
                ViewModel model;
                switch (curMissile.getType()){
                    case (Missile.LASER):
                        model = new ViewModel(view_of_Laser);
                        break;
                    case (Missile.BIG_LASER):
                        model = new ViewModel(view_of_Big_Laser);
                        break;
                    default:
                        model = new ViewModel();

                }
                model.setX_cord(curMissile.getX_cord()-model.getWidth()/2);
                model.setY_cord(curMissile.getY_cord()-model.getHeight()/2);
                model.setDirection(curMissile.getDirection());
                models.add(model);
            }
            if(mobs.get(0).getClass() == Player.class){
                graphicsModule.setHP((int)Math.round(mobs.get(0).getHealth()));
            }
            calculate_mobs();

            for (int i = 0; i < mobs.size(); i++){
                Mob curMob = mobs.get(i);
                ViewModel model;
                if (Player.class.equals(curMob.getClass())) {
                    model = new ViewModel(view_of_Player);
                } else if (TIE_Fighter.class.equals(curMob.getClass())) {
                    model = new ViewModel(view_of_TIE_Fighter);
                } else if (Monster.class.equals(curMob.getClass())) {
                    model = new ViewModel(view_of_Monster);
                } else {
                    model = new ViewModel();
                }
                model.setX_cord(curMob.getX_cord()-model.getWidth()/2);
                model.setY_cord(curMob.getY_cord()-model.getHeight()/2);
                model.setDirection(curMob.getDirection());
                models.add(model);
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
            tick_main++;
        }
    }
}
