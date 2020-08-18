package app;

//Class that can decorate a shape
//BaseDecorator has a field for referencing a wrapped object 
//The base decorator delegates all operations to the wrapped object
public abstract class BaseDecorator implements IShapes 
{
    //Shape to decorate
    protected BaseShape decoratedShape;
   
    //constructor
    public BaseDecorator(BaseShape decoratedShape)
    {
       this.decoratedShape = decoratedShape;
    }
    
    //Draw the given shape
    public void draw(int sx, int sy, int ex, int ey)
    {
       decoratedShape.draw(sx, sy, ex, ey);
    }	
 }