package app;

import java.awt.geom.*;
//Visitor Pattern
//part of not implemented/ template code for visitor!
//Create a concrete class exentding the visitable interface
public class Rectangle extends BaseShape implements IVisitable 
{
    //Define the parameters of the constructor of the baseShape
    Rectangle() 
    {
        super(new DrawRectangleStrategy(), new MoveRectangleStrategy(), new ResizeRectangleStrategy(), new Rectangle2D.Float());
    }
    //accept the visitor
    @Override
    public String accept(IVisitor visitor) 
    {
       return visitor.visit(this);
    }

    //Clone the current Rectangle Shape and return the cloned shape
    @Override
    public BaseShape clone() 
    {
        BaseShape newbase;
        newbase = new Rectangle();
        newbase.shape = new Rectangle2D.Float(getX(), getY(), getWidth(), getHeight());
        for (Text text : textList) 
        {
            newbase.addText(text.textPosition, text.text);
        }
        return newbase;
    }
}
