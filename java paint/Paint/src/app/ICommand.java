package app;

 // Interface serves as the "Order" for the Command Pattern
 //command pattern is still being tested!
 //this part however should work since its just an interface with a void
@FunctionalInterface
public interface ICommand
{
    void execute();
}