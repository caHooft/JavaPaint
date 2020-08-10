package app;

//Write to file
//needs serieus look
//the fundemental idea works
//problem probebly with selecting /saving of groups
public class SaveCommand implements ICommand
{
    private ShapeActions ShapeActions;

    //constructor
    public SaveCommand(ShapeActions ShapeActions)
    {
        this.ShapeActions = ShapeActions;
    }
    
    //execute the command
    public void execute()
    {
        ShapeActions.save();
    }
}