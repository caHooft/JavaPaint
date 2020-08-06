package app;

import java.awt.*;
import java.awt.geom.Rectangle2D;

//This is the implementation part of the strategy pattern
//here's the actual code that is being used when drawing a rectangle

class DrawRectangleStrategy implements IDrawStrategy 
{
    public void draw(Shape shape, int sx, int sy, int ex, int ey) 
    {
        Rectangle2D.Float rectangle = (Rectangle2D.Float) shape;
        rectangle.setRect(Math.min(sx, ex), Math.min(sy, ey), Math.abs(sx - ex), Math.abs(sy - ey));
    }
}