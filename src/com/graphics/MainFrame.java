package com.graphics;

import com.ViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;

public class MainFrame extends JFrame {
    Board mainboard = null;

    private boolean[] keysstatus = new boolean[256];

    MainFrame(int xsize, int ysize ){
        setTitle("Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

    ArrayList<ViewModel> models = new ArrayList<ViewModel>();

    public Board(int xsize, int ysize) {
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
        drawModels(g);

        // this smooths out animations on some systems
        //Toolkit.getDefaultToolkit().sync();
    }

    public void drawModels(Graphics g){
        for (int i = 0; i<models.size(); i++){
            g.drawImage(models.get(i).getImage(), (int)models.get(i).getX_cord(), (int)models.get(i).getY_cord(),
                    (int)models.get(i).getWidth(), (int)models.get(i).getHeight(), null);
        }

    }

    public void clearModels(){
        models.clear();
    }

    public void addModel(ViewModel model) {
        models.add(model);
    }


}
