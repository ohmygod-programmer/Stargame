package com.graphics;

import com.ViewModel;
import com.graphics.MainFrame;

import java.util.Collection;

import static java.lang.Thread.sleep;

public class GraphicsModule {
    MainFrame mainFrame = new MainFrame(800, 600);
    public void startmainframe(){
        mainFrame.startshowing();
    }
    public void putmodels(Collection<ViewModel> c){
        mainFrame.addmodelstomain(c);
    }
    public void refreshframe(){
        mainFrame.refresh();
        try{
            sleep(1);
        }
        catch (Exception e){

        }
        mainFrame.clearmain();
    }
    public boolean getkeystatus(int code){
        return mainFrame.getkeystatus(code);
    }



}
