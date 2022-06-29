package com;


import com.graphics.displayable;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ViewModel {
    BufferedImage image;
    double height;
    double width;
    protected double direction = 0; //in degrees, North = 0
    //    public ViewModel(Image img){
//        image = img;
//        height = img.getHeight(null);
//        width = img.getWidth(null);
//    }
    public BufferedImage getImage(){
        return image;
    }
    public void setImage(BufferedImage img){
        image = img;
    }
    public double getHeight() {
        return height;
    }
    public void setHeight(double h) {
        height = h;
    }
    public double getWidth() {
        return width;
    }
    public void setWidth(double w){
        width = w;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    double x_cord = 0;
    double y_cord = 0;

    public void move(double newx, double newy){
        x_cord = newx;
        y_cord = newy;
    }

    public double getX_cord(){
        return x_cord;
    }

    public void setX_cord(double x){
        x_cord = x;
    }

    public double getY_cord(){
        return y_cord;
    }
    public void setY_cord(double y){
        y_cord = y;
    }

    public ViewModel(){}

    public ViewModel(ViewModel model){
        image = model.image;
        height = model.height;
        width = model.width;
        x_cord = model.x_cord;
        y_cord = model.y_cord;
    }
}

