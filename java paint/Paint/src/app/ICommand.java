package app;

 // Interface serves as the "Order" for the Command Pattern
 //command pattern is still being tested!
 //this part however should work since its just an interface with a void

 //this interface is simply the start of my command pattern
 //all actual "Commands" need to implement this to indicate that they are a command
 //this interface serves as a hub for all the commands so that they all have to implement execute
 //i named it execute across the board to prevent confusion

public interface ICommand
{
    void execute();
    
    //i want to try to add an Unexecute method here cuz that would make sence
    //experimental though
    void unExecute();
}