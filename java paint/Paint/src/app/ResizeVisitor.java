package app;

import java.awt.*;

//Visitor Pattern
//this is technicclly correct but it dont think it DOES anything maybe change what it does or how its called!

class ResizeVisitor implements IVisitor
{

    @Override
    public void visit(Circle circle)
    {
        //resize((Shape) circle, 10, 10);
        System.out.println("circle in visitor");
    }

    @Override
    public void visit(Rectangle rectangle) 
    {
        //resize((Shape) rectangle, 10, 10);
        System.out.println("rectangle in visitor");
    }

    //@Override
    //public void resize(Shape shape, int width, int height) 
    //{

    //}

}


/*
class ResizeVisitor implements IVisitor 
{

    private Point point;

    //Constructor
    public ResizeVisitor(Point point) 
    {
        this.point = point;
    }

    public void Resize(Shape ResizedShape) 
    {
        
    }
    //Visit circle
    @Override
    public void visit(Circle circle) 
    {
        System.out.println("circle in visitor");
        Resize((Shape) circle);
    }
    
    //Visit rectangle
    @Override
    public void visit(Rectangle rectangle) 
    {
        
        System.out.println("rectangle in visitor");
        Resize((Shape) rectangle);
    }

}
*/