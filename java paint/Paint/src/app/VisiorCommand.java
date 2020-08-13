package app;

//command pattern
//used to debug visitor
//is not part of the actual assignment and used for debugging
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
    
    //Execute the debug command
    public void execute()
    {
        //System.out.println(circle.accept(debug) +"henk");

        circle.accept(resize);
    }
}