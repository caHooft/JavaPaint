package app;

//Start of the application
//if the launch file isnt working correctly run the app from this script manually!
public class Start 
{
  public static void main(String[] args) 
  {
    //Sets a vairable set to the frame instance Singleton Pattern
    //Here it is very important to use the singleton pattern since multiple instances of the actual paint interface would cause many problems
    //useing the singleton pattern here insures this type of problem wont occur!
    PaintInterface object = PaintInterface.getInstance();
    object.createDrawObj();
  }
}