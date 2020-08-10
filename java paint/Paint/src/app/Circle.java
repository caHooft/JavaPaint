package app;

import java.awt.geom.*;
//Visitor Pattern

//In my composite pattern i use base shape as a container class/composite class
//base shape in this project represents my complex elements
//base shape delegates most f the work to his sub elements
//sub elements of base shape are circle and rectangle
//when i call a method here the objects that implements this class pass the request down the "tree" of the composite pattern

//Create a concrete class exentding the visitable interface
public class Circle extends BaseShape implements IVisitable 
{
    // Call the BaseShape constructor and fill in the behaviours.
    Circle() 
    {
        super(new DrawCircleStrategy(), new MoveCircleStrategy(), new ResizeCircleStrategy(), new Ellipse2D.Float());
    }

    // Accept the visitor
    @Override
    public void accept(IVisitor visitor) 
    {
        visitor.visit(this);
    }
    //Clone the current Circle Shape and return the cloned shape
    @Override
    public BaseShape clone() 
    {
        BaseShape newbase;
        newbase = new Circle();
        newbase.shape = new Ellipse2D.Float(getX(), getY(), getWidth(), getHeight());
        
        for (Text text : textList) 
        {
            newbase.addText(text.textPosition, text.text);
        }
        
        return newbase;
    }

}
