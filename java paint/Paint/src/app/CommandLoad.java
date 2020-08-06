package app;

//command pattern
//load shape command
public class CommandLoad implements ICommand
{
    private ShapeActions ShapeActions;

    //constructor
    public CommandLoad(ShapeActions ShapeActions)
    {
        this.ShapeActions = ShapeActions;
    }
    
    //Execute the command
    public void execute(){
        ShapeActions.load();
    }
}