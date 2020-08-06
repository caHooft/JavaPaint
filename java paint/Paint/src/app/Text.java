package app;

//This class handles text adding to shapes
//it get the requested position and the text
//from that point in set pos we calculate the exact x and y values of where the text will be
public class Text
 {
    int x, y;
    String text;
    String textPosition;

    //Constructor
    public Text(String text, String position) 
    {
        this.text = text;
        this.textPosition = position;
    }
    
    //Set position of the text
    public void setPos(int shapeX, int shapeY, int shapeWidth, int shapeHeight) 
    {
        switch (textPosition)
        {
        case "Top":
            x = (shapeX + (shapeWidth / 3));
            y = (int) shapeY -5;
            break;
        case "Bottom":
            x = (int) (shapeX + (shapeWidth / 3)) +5;
            y = (int) (shapeY + (shapeHeight + 20));
            break;
        case "Left":
            x = (int) (shapeX - (shapeWidth / 4));
            y = (int) (shapeY + (shapeHeight / 2));
            break;
        case "Right":
            x = (int) (shapeX + shapeWidth +5);
            y = (int) (shapeY + (shapeHeight / 2));
            break;
        default:
            break;
        }
    }
}