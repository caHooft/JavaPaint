package app;

//Decorates a shape
//this SHOULD work but isnt tested 
public class Decorator extends ShapeDecorator 
{
   //constructor
   public Decorator(BaseShape decoratedShape) 
   {
      super(decoratedShape);
   }
   //draw the given shape
   @Override
   public void draw(int sx, int sy, int ex, int ey) 
   {
      decoratedShape.draw(sx, sy, ex, ey);
   }
   //add text to the given shape
   @Override
   public void addText(String position, String text) 
   {

   }
}