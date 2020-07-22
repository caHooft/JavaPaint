package app;

//start of app
public class Start
{
  public static void main(String[] args) 
  {
    //Sets a vairable set to the frame instance
    PaintSurface window = PaintSurface.getInstance();
    window.repaint();
  }
}