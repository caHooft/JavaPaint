package app;

//command pattern
//Clear shape command
public class CommandClear implements ICommand
{
    private ShapeActions ShapeActions;

    //Constructor
    public CommandClear(ShapeActions ShapeActions)
    {
        this.ShapeActions = ShapeActions;
    }
    
    //Execute the clear command
    public void execute()
    {
        ShapeActions.clear();
    }
}