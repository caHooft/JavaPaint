package app;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

//Strategy Pattern
//Move rectangle to another position on the frame, implements IMoveStrategy
class MoveRectangleStrategy implements IMoveStrategy 
{
    public void move(Shape shape, int x, int y) 
    {
        Rectangle2D.Float rectangle = (Rectangle2D.Float) shape;
        rectangle.setRect(x, y, rectangle.width, rectangle.height);
    }
}