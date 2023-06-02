package com.graphics;

import com.ViewModel;
import com.StarGameApplication;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

public class MainFrame extends JFrame {
    Board mainboard = null;

    private boolean[] keysstatus = new boolean[256];

    MainFrame(int xsize, int ysize ){
        setTitle("Game");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new KL());
        mainboard =  new Board(xsize, ysize);
        add(mainboard);
        setResizable(false);
    }

    public void startshowing(){
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void refresh(){
        mainboard.repaint();
        }

    public boolean getkeystatus(int code){
        return keysstatus[code];
    }



    public void addmodelstomain(Collection<ViewModel> collection) {
        for (ViewModel model : collection) {
            mainboard.addModel(model);
        }
    }
    public void clearmain() {
        mainboard.clearModels();
    }
    public void setHP(int hp){
        mainboard.setHP(hp);
    }
    public class KL extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent event) {
            keysstatus[event.getKeyCode()] = true;
        }

        @Override
        public void keyReleased(KeyEvent event) {
            keysstatus[event.getKeyCode()] = false;
        }
    }
}



class Board extends JPanel {

    private BufferedImage tryreadimg(String s){
        BufferedImage img;
        try {
            img= ImageIO.read(getClass().getResource(s));//new ImageIcon("src/laser.png").getImage();

        }
        catch (Exception e){
            img = errorimg;
        }
        return img;
    }
    private void setsizeandimage(ViewModel model, BufferedImage img){
        model.setImage(img);
        model.setHeight(hp_height);
        model.setWidth(img.getWidth()*((float)hp_height/img.getHeight()));
    }
    int player_hp = 100;
    static int hp_height = 20;
    static BufferedImage sky;
    static ViewModel HPone = new ViewModel();
    static ViewModel HPtwo = new ViewModel();
    static ViewModel HPthree = new ViewModel();
    static ViewModel HPfour = new ViewModel();
    static ViewModel HPfive = new ViewModel();
    static ViewModel HPsix = new ViewModel();
    static ViewModel HPseven = new ViewModel();
    static ViewModel HPeight = new ViewModel();
    static ViewModel HPnine = new ViewModel();
    static ViewModel HPzero = new ViewModel();
    ArrayList<ViewModel> models = new ArrayList<ViewModel>();
    public static BufferedImage errorimg = null;
    public Board(int xsize, int ysize) {
        errorimg = tryreadimg("/Error.jpg");
        sky = tryreadimg("/sky.jpg");
        BufferedImage img_of_hpone = tryreadimg("/1.png");
        BufferedImage img_of_hptwo = tryreadimg("/2.png");
        BufferedImage img_of_hpthree = tryreadimg("/3.png");
        BufferedImage img_of_hpfour = tryreadimg("/4.png");
        BufferedImage img_of_hpfive = tryreadimg("/5.png");
        BufferedImage img_of_hpsix = tryreadimg("/6.png");
        BufferedImage img_of_hpseven = tryreadimg("/7.png");
        BufferedImage img_of_hpeight = tryreadimg("/8.png");
        BufferedImage img_of_hpnine = tryreadimg("/9.png");
        BufferedImage img_of_hpzero = tryreadimg("/0.png");
        setsizeandimage(HPone, img_of_hpone);
        setsizeandimage(HPtwo, img_of_hptwo);
        setsizeandimage(HPthree, img_of_hpthree);
        setsizeandimage(HPfour, img_of_hpfour);
        setsizeandimage(HPfive, img_of_hpfive);
        setsizeandimage(HPsix, img_of_hpsix);
        setsizeandimage(HPseven, img_of_hpseven);
        setsizeandimage(HPeight, img_of_hpeight);
        setsizeandimage(HPnine, img_of_hpnine);
        setsizeandimage(HPzero, img_of_hpzero);


        // set the game board size
        setPreferredSize(new Dimension(xsize, ysize));
        // set the game board background color
        setBackground(new Color(50, 50, 50));

        // initialize the game state
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // when calling g.drawImage() we can use "this" for the ImageObserver
        // because Component implements the ImageObserver interface, and JPanel
        // extends from Component. So "this" Board instance, as a Component, can
        // react to imageUpdate() events triggered by g.drawImage()

        // draw our graphics.
        long tick = StarGameApplication.getTick_main();
        g.drawImage(sky, 0, (int)(tick/3 % sky.getHeight()) - sky.getHeight(), sky.getWidth(), sky.getHeight(), null);
        g.drawImage(sky, 0, (int)(tick/3 % sky.getHeight()), sky.getWidth(), sky.getHeight(), null);
        drawModels(g);
        drawHP(g);
        // this smooths out animations on some systems
        //Toolkit.getDefaultToolkit().sync();
    }

    public void drawModels(Graphics g){
        for (int i = 0; i<models.size(); i++){
            ViewModel curModel = models.get(i);
            BufferedImage img = curModel.getImage();
            if (curModel.getDirection() != 0){
                AffineTransform tx = new AffineTransform();
                double angel = -Math.toRadians(curModel.getDirection());
                int w = img.getWidth();
                int h = img.getHeight();
                tx.rotate(angel, w/2, h/2);
                AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
                img = op.filter(img, null);
            }
            g.drawImage(img, (int)curModel.getX_cord(), (int)curModel.getY_cord(),
                    (int)curModel.getWidth(), (int)curModel.getHeight(), null);

        }

    }

    public void drawHP(Graphics g){
        int x = StarGameApplication.XWINDOWSIZE-10;
        int y = StarGameApplication.YWINDOWSIZE-30;
        int hp = player_hp;
        double width = 0;
        BufferedImage img = null;
        while (hp != 0){
            int num = hp%10;
            hp/=10;
            switch (num){
                case (0):
                    img = HPzero.getImage();
                    width = HPzero.getWidth();
                    break;
                case (1):
                    img = HPone.getImage();
                    width = HPone.getWidth();
                    break;
                case (2):
                    img = HPtwo.getImage();
                    width = HPtwo.getWidth();
                    break;
                case (3):
                    img = HPthree.getImage();
                    width = HPthree.getWidth();
                    break;
                case (4):
                    img = HPfour.getImage();
                    width = HPfour.getWidth();
                    break;
                case (5):
                    img = HPfive.getImage();
                    width = HPfive.getWidth();
                    break;
                case (6):
                    img = HPsix.getImage();
                    width = HPsix.getWidth();
                    break;
                case (7):
                    img = HPseven.getImage();
                    width = HPseven.getWidth();
                    break;
                case (8):
                    img = HPeight.getImage();
                    width = HPeight.getWidth();
                    break;
                case (9):
                    img = HPnine.getImage();
                    width = HPnine.getWidth();
                    break;
                default:
                    img = errorimg;
                    width = errorimg.getWidth();
            }
            x-=Math.round(width);

            g.drawImage(img, x, y,
                    (int)Math.round(width), hp_height, null);

        }
    }

    public void clearModels(){
        models.clear();
    }

    public void addModel(ViewModel model) {
        models.add(model);
    }
    public void setHP(int hp){
        player_hp = hp;
    }

}
