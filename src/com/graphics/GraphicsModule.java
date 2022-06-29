package com.graphics;

import com.ViewModel;
import com.graphics.MainFrame;

import java.util.Collection;

import static java.lang.Thread.sleep;

public class GraphicsModule {
    int xsize;
    int ysize;
    MainFrame mainFrame;
    public GraphicsModule(int x, int y){
        xsize = x;
        ysize = y;
        mainFrame = new MainFrame(xsize, ysize);
    };

    public void startmainframe(){
        mainFrame.startshowing();
    }
    public void putmodels(Collection<ViewModel> c){
        mainFrame.addmodelstomain(c);
    }
    public void refreshframe(){
        mainFrame.refresh();
    }
    public void setHP(int hp){
        mainFrame.setHP(hp);
    };
    public void clearmodels(){
        mainFrame.clearmain();
    }
    public boolean getkeystatus(int code){
        return mainFrame.getkeystatus(code);
    }



}
