package app;

import java.awt.Point;
//Visitor Pattern
//this is technicclly correct but it dont think it DOES anything maybe change what it does or how its called!
/*
class MoveVisitor implements IVisitor 
{
    //Define variable
    private Point point;
    
    //Constructor
    public MoveVisitor(Point point) 
    {
        this.point = point;
    }

    //Move function which moves the shape
    public void Move(BaseShape moveShape) 
    {
        moveShape.move(point.x, point.y);
    }

    //Add visitor circle
    @Override
    public void visit(Circle circle) 
    {
        System.out.print("circle in visitor move");
        Move((BaseShape) circle);
    }

    //Add visitor rectangle
    @Override
    public void visit(Rectangle rectangle) 
    {
        System.out.print("recrangle in visitor move");
        Move((BaseShape) rectangle);
    }
}*/