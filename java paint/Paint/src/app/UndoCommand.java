package app;

//Command that will undo the previous action
public class UndoCommand implements ICommand
{
    private ShapeActions ShapeActions;
    //constructor
    public UndoCommand(ShapeActions ShapeActions)
    {
        this.ShapeActions = ShapeActions;
    }
    //executes the command
    public void execute()
    {
        ShapeActions.undo();
    }
}