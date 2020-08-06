package app;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

//Strategy Pattern
//Move circle to another position on the frame, implements IMoveStrategy
class MoveCircleStrategy implements IMoveStrategy 
{
    public void move(Shape shape, int x, int y) 
    {
        Ellipse2D.Float ellipse = (Ellipse2D.Float) shape;
        ellipse.setFrame(x, y, ellipse.width, ellipse.height);
    }
}