package app;

//command pattern
//Redo shape command
public class CommandRedo implements ICommand
{
    private ShapeActions ShapeActions;

    //Constructor
    public CommandRedo(ShapeActions ShapeActions)
    {
        this.ShapeActions = ShapeActions;
    }
    
    //Execute the command
    public void execute()
    {
        ShapeActions.redo();
    }
}