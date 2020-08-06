package app;

import java.awt.*;
import java.awt.geom.Ellipse2D;

//This is the implementation part of the strategy pattern
//here's the actual code that is being used when drawing a circle
class DrawCircleStrategy implements IDrawStrategy 
{
    public void draw(Shape shape, int sx, int sy, int ex, int ey) 
    {
        Ellipse2D.Float ellipse = (Ellipse2D.Float) shape;
        ellipse.setFrame(Math.min(sx, ex), Math.min(sy, ey), Math.abs(sx - ex), Math.abs(sy - ey));
    }
}