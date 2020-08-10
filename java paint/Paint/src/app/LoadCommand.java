package app;

//command pattern
//load shape command
public class LoadCommand implements ICommand
{
    private ShapeActions ShapeActions;

    //constructor
    public LoadCommand(ShapeActions ShapeActions)
    {
        this.ShapeActions = ShapeActions;
    }
    
    //Execute the command
    public void execute()
    {
        ShapeActions.load();
    }

    public void unExecute()
    {
        ShapeActions.undo();
    }
}