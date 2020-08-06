package app;

import java.awt.*;
import java.util.*;
import java.util.List;

//uses the composite pattern

//in my composite pattern i use base shape as a container class/composite class
//base shape in this project represents my complex elements
//base shape delegates most f the work to his sub elements
//sub elements of base shape are circle and rectangle
//when i call a method here the objects that implements this class pass the request down the "tree" of the composite pattern

//uses the strategy pattern
//uses the singleton pattern
public abstract class BaseShape implements IShapes
{
    //Define variable for the Drawbehaviour Strategy Pattern
    IDrawStrategy drawBehaviour;

    //Define variable for the MoveBehaviour Strategy Pattern
    IMoveStrategy moveBehaviour;

    //Define variable for the ResizeBehaviour Strategy Pattern
    IResizeStrategy resizeBehaviour;
    
    //here i make a Arraylist of the class base text
    ArrayList<Text> textList = new ArrayList<Text>();

    //composite pattern
    //Create list of BaseShapes who are in the same shapeGroup 
    List<BaseShape> shapeGroup;
     
    Shape shape;

    //I use the singleton pattern here to set a single instance of ShapeActions
    ShapeActions actions = ShapeActions.getInstance();
    
    //Constructor
    public BaseShape(IDrawStrategy drawBehaviour, IMoveStrategy moveBehaviour, IResizeStrategy resizeBehaviour, Shape shape) 
    {
        this.drawBehaviour = drawBehaviour;
        this.moveBehaviour = moveBehaviour;
        this.resizeBehaviour = resizeBehaviour;
        this.shape = shape;
    }
    //Clone the BaseShape
    public abstract BaseShape clone();

        //add the shape through the ShapeActions instance Command Pattern
        public void AddToArray() 
        {
            actions.addShapeToArray(this);
        }
    
        //Composite Pattern
        //Create a list of shapes who are in the same shapeGroup
        //grouping selection doesnt work all the time
        //Not sure why, YET!
        public void CreateGroup(BaseShape addShape)
        {
            if(shapeGroup == null)
            {
                shapeGroup = new ArrayList<BaseShape>();
                shapeGroup.add(addShape);
            }
            
            else
            {
                shapeGroup.add(addShape);
            }
        }
        
    //Returns the Group of shapes
    public List<BaseShape> GetGroup()
    {
        return shapeGroup;
    }

    //returns the X position of the shape
    public int getX() 
    {
        return (int) shape.getBounds2D().getX();
    }

    //Returns the Y position of the shape
    public int getY() 
    {
        return (int) shape.getBounds2D().getY();
    }

    //Returns the height of the shape
    public int getHeight() 
    {
        return (int) shape.getBounds2D().getHeight();
    }

    //Returns the width of the shape
    public int getWidth() 
    {
        return (int) shape.getBounds2D().getWidth();
    }

    //Call the move function from IMoveStrategy and give it the parameters from the constructor Strategy Pattern
    public void move(int x, int y) 
    {
        this.moveBehaviour.move(shape, x, y);
        updateTextPos();
    }

    //Call the resize function from IResizeStrategy give it the parameters from the constructor Strategy Pattern
    public void resize(int width, int height) 
    {
        this.resizeBehaviour.resize(shape, width, height);
        updateTextPos();
    }

    //Call the draw function from IDrawStrategy give it the parameters from the constructor Strategy Pattern
    public void draw(int sx, int sy, int ex, int ey) 
    {        
        this.drawBehaviour.draw(shape, sx, sy, ex, ey);
        AddToArray();
    }
    
    // the shape to text
    public String toString() 
    {
        String s = "";
        s += "x = " + getX();
        s += " | y = " + getY();
        s += " | width = " + getWidth();
        s += " | height = " + getHeight();
        return s;
    }

    //Add text to shape
    public void addText(String shapeTextPosition, String textString)
     {
        for(Text text : textList)
        {
            if(shapeTextPosition == text.textPosition)
            {
                text.text = textString;
                return;
            }
        }    

        Text text = new Text(textString, shapeTextPosition);
        text.setPos(getX(), getY(), getWidth(), getHeight());
        textList.add(text);
    }

    //Update the position of the text
    public void updateTextPos()
    {
        for(Text text : textList)
        {
            text.setPos(getX(), getY(), getWidth(), getHeight());
        }
    }
}