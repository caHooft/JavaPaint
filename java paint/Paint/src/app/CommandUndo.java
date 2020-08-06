package app;

//Command that will undo the previous action
public class CommandUndo implements ICommand
{
    private ShapeActions ShapeActions;
    //constructor
    public CommandUndo(ShapeActions ShapeActions)
    {
        this.ShapeActions = ShapeActions;
    }
    //executes the command
    public void execute()
    {
        ShapeActions.undo();
    }
}