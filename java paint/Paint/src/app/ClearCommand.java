package app;

//command pattern
//Clear shape command
public class ClearCommand implements ICommand
{
    private ShapeActions ShapeActions;

    //Constructor
    public ClearCommand(ShapeActions ShapeActions)
    {
        this.ShapeActions = ShapeActions;
    }
    
    //Execute the clear command
    public void execute()
    {
        ShapeActions.clear();
    }

    public void unExecute()
    {
        ShapeActions.undo();
    }
}