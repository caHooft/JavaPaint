package app;

//Decorates a shape
//ConcreteDecorator defines extra behaviors that can be added to components dynamically. 
//Concrete decorators overrides methods of the base decorator and execute their behavior either before or after calling the parent method.
public class ConcreteDecorator extends BaseDecorator 
{
   //constructor
   public ConcreteDecorator(BaseShape decoratedShape) 
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
      //
   }
}