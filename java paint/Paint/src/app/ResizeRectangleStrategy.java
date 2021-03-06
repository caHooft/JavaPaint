package app;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

//<<Strategy Pattern>>
//Resize the recatangle, implements IResizeStrategy
class ResizeRectangleStrategy implements IResizeStrategy 
{
    public void resize(Shape shape, int endX, int endY) 
    {
        Rectangle2D.Float rectangle =(Rectangle2D.Float) shape;
        int shapeX = (int) rectangle.getBounds2D().getX();
        int shapeY = (int) rectangle.getBounds2D().getY();

        rectangle.setFrame(Math.min(shapeX, endX), Math.min(shapeY, endY),
        Math.abs(shapeX - endX), Math.abs(shapeY - endY));
    }
}