package app;

//Class that can decorate a shape
//This script is under inspection!
//no direct errors but needs inspecting!
public abstract class ShapeDecorator implements IShapes 
{
    //Shape to decorate
    protected BaseShape decoratedShape;
   
    //constructor
    public ShapeDecorator(BaseShape decoratedShape)
    {
       this.decoratedShape = decoratedShape;
    }
    
    //Draw the given shape
    public void draw(int sx, int sy, int ex, int ey)
    {
       decoratedShape.draw(sx, sy, ex, ey);
    }	
 }