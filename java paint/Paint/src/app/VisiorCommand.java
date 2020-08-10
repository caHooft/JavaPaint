package app;

//command pattern
//text adding
//this command pattern is in  use under the text button!
//sadly i cant get tis script to do what i want it to
//right now it just functions as an undo untill i get an actual idea/plan on how to make it work
public class VisiorCommand implements ICommand
{
    //DebugVisitor debug = new DebugVisitor();
    ResizeVisitor resize = new ResizeVisitor();

    Circle circle = new Circle();
    
    private ShapeActions ShapeActions;

    //Constructor
    public VisiorCommand(ShapeActions ShapeActions)
    {
        this.ShapeActions = ShapeActions;
    }
    
    //Execute the clear command
    public void execute()
    {
        //System.out.println(circle.accept(debug) +"henk");

        circle.accept(resize);
    }
}