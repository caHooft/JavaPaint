package app;

//command pattern
//Redo shape command
public class RedoCommand implements ICommand
{
    private ShapeActions ShapeActions;

    //Constructor
    public RedoCommand(ShapeActions ShapeActions)
    {
        this.ShapeActions = ShapeActions;
    }
    
    //Execute the command
    public void execute()
    {
        ShapeActions.redoMove();
    }
}