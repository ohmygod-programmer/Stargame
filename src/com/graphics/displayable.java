package com.graphics;

import java.awt.*;

public interface displayable{
    Image getImage();
    void setImage(Image img);
    double getHeight();
    double getWidth();
    double getX_cord();
    double getY_cord();
    void setHeight(double h);
    void setWidth(double w);
    void setX_cord(double x);
    void setY_cord(double y);
}
