package app;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.Font;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JOptionPane;


public class PaintWindow extends JComponent 
{
  //Create new instance, i use a singleton here because i only want one instance
  //also it needs to be globally available
  private static PaintWindow instance = new PaintWindow();

  private Point startDrag; 
  private Point endDrag ;
  private Point clickedPoint;

  private boolean dragging = false;

  //Sets a vairable set to the frame instance Singleton Pattern
  private PaintInterface frame = PaintInterface.getInstance();

  //Sets a vairable set to the ShapeActions instance Singleton Pattern
  private ShapeActions actions = ShapeActions.getInstance();

  private int oldSX;
  private int oldSY;
  private int shapeValue = -1;

  public String shapeType = frame.getShapeType();
  public ArrayList<BaseShape> shapes = new ArrayList<BaseShape>();
  private ArrayList<BaseShape> availableShapes = new ArrayList<BaseShape>();

  //Returns the paintsurface instance Singleton Pattern
  //we only want 1 paintSurface
  //we want it to be globally available
  public static PaintWindow getInstance() 
  {
    return instance;
  }

  public PaintWindow() 
  {
    //Checks mouse actions
    this.addMouseListener(new MouseAdapter() 
    {
      //Define position of the mouse by mousePressed location
      public void mousePressed(MouseEvent mouseEvent) 
      {
        shapeType = frame.getShapeType();

        startDrag = new Point(mouseEvent.getX(), mouseEvent.getY());
        clickedPoint = new Point(mouseEvent.getX(), mouseEvent.getY());
        clickedPoint.x = mouseEvent.getPoint().x;
        clickedPoint.y = mouseEvent.getPoint().y;
        endDrag = startDrag;

        //Add text to a shape
        if (shapeType == "Top" || shapeType == "Bottom" || shapeType == "Left" || shapeType == "Right") 
        {
          for (BaseShape baseShape : shapes) 
          {
            if (baseShape.shape.contains(clickedPoint.x, clickedPoint.y)) 
            {
              actions.saveAction();
              String text = JOptionPane.showInputDialog("Enter text");
              baseShape.addText(shapeType, text);
            }
          }
        }
        repaint();
      }

      //Draw or change the shapes based on selected option
      public void mouseReleased(MouseEvent mouseEvent) 
      {
        availableShapes.clear();
        if (shapeType == "Rectangle") 
        {
          IShapes rectangle = new ConcreteDecorator(new Rectangle());
          rectangle.draw(startDrag.x, startDrag.y, mouseEvent.getX(), mouseEvent.getY());
        }
        
        else if (shapeType == "Oval") 
        {
          IShapes oval = new ConcreteDecorator(new Circle());
          oval.draw(startDrag.x, startDrag.y, mouseEvent.getX(), mouseEvent.getY());
        }
        else if(shapeType == "Group")
        {
          //counts number of shapes
          int sCount = -1;

          //here i make the base for the new group
          List<BaseShape> newGroup = new ArrayList<BaseShape>();
          
          for (BaseShape baseShape : shapes)
          {
            sCount++;
              for (BaseShape x : shapes)
              {
                //this should detect all shapes inside the dragged box!
                if(x.getX() >= (Math.min(startDrag.x, endDrag.x)) && 
                   x.getY() >= (Math.min(startDrag.y, endDrag.y)) &&
                   x.getX() + x.getWidth() <= Math.max(startDrag.x, endDrag.x) &&
                   x.getY() + x.getHeight() <= Math.max(startDrag.y, endDrag.y))
                {
                    if(baseShape != x)
                    {
                      newGroup.add(x);
                    }
                }
                    //when the code doesnt detect any shapes (circles and rectangles)
                    //this else statement is triggered to warn the user that it failed to detect the shapes
                    else
                    {
                      System.out.print("no shapes");
                    }
              
              //here i give back the new group with number sCount en the shapes in the group are given as newGroup
              group(sCount, newGroup);
              actions.saveAction();
            }
          }
        }
        
        else if(shapeType == "Ungroup")
        {
          int sCount = -1;

          //this should detect all shapes inside the dragged are and than ungroup them
          for (BaseShape baseShape : shapes)
          {
            sCount++;
            if(baseShape.shape.contains(clickedPoint.x, clickedPoint.y) && baseShape.GetGroup() != null)
            {
              ungroup(sCount);
            }
            //when the code doesnt detect any shapes (circles and rectangles) that are part of the group this triggers
            //this else statement is triggered to warn the user that it failed to detect the shapes
            //there are some issues here sometimes this doesnt trigger when it should
            else
            {
              System.out.println("No group found in this shape/ not  parent of a group");
            }
          }
        }
        if (shapeValue != -1) 
        {
          if (shapeType == "Select") 
          {
            if (dragging == true) 
            {
              DragObject(shapeValue, mouseEvent.getPoint());
            }
          } 
          else if (shapeType == "Resize") 
          {
            if (dragging == true) 
            {
              ResizeObject(shapeValue, mouseEvent.getPoint());
            }
          }
        }

        startDrag = null;
        endDrag = null;
        shapeValue = -1;
        dragging = false;
        repaint();
      }

    });

    //move the selected shape to a new location based on the mouse position where the user stops dragging
    //or resize the selected shape based on new mouse position where the user stops dragging
    this.addMouseMotionListener(new MouseMotionAdapter() 
    {
      public void mouseDragged(MouseEvent mouseEvent) 
      {
        endDrag = new Point(mouseEvent.getX(), mouseEvent.getY());
        repaint();
        if (shapeValue != -1) 
        {
          dragging = true;
          if (shapeType == "Resize") 
          {
            BaseShape bs = shapes.get(shapeValue);

            startDrag.x = bs.getWidth();
            startDrag.y = bs.getHeight();
          } 
          
          else if (shapeType == "Select") 
          {
            startDrag.x = mouseEvent.getPoint().x;
            startDrag.y = mouseEvent.getPoint().y;
          }
        }
      }
    });
  }

  //Generates the background squares
  private void paintBackground(Graphics2D g2d) 
  {
    g2d.setPaint(Color.LIGHT_GRAY);
    for (int i = 0; i < getSize().width; i += 25) 
    {
      Shape line = new Line2D.Float(i, 0, i, getSize().height);
      g2d.draw(line);
    }

    for (int i = 0; i < getSize().height; i += 25) 
    {
      Shape line = new Line2D.Float(0, i, getSize().width, i);
      g2d.draw(line);
    }
  }

  //Handels the "looks" side of things  
  //things like text color size and font
  //also shape/figure colours
  public void paint(Graphics graphics)
  {
    if (shapeType != "Resize" || shapeType == "Select") 
    {
      availableShapes.clear();
    }

    Graphics2D graphics2d = (Graphics2D) graphics;
    graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    paintBackground(graphics2d);

    //define colours
    Color[] colors = {Color.GREEN, Color.BLACK, Color.RED, Color.BLUE };
    //define color index
    int colorIndex = 0;

    graphics2d.setStroke(new BasicStroke(2));
    graphics2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.50f));
    for (BaseShape bs : shapes) 
    {
      if (startDrag != null && bs != null && shapeType == "Resize" || shapeType == "Select") 
      {
        if (bs.shape.contains(clickedPoint.x, clickedPoint.y)) 
        {
          if (!availableShapes.contains(bs)) 
          {
            availableShapes.add(bs);
          }
          shapeValue = shapes.indexOf(bs);
          graphics2d.setStroke(new BasicStroke(10));
        }
      }

      // this sets the text font/size 
      graphics2d.setFont(new Font("Georgia", Font.BOLD, 20));
      graphics2d.setPaint(Color.BLACK);
      if (bs.textList.size() != 0) 
      {
        for (Text text : bs.textList) 
        {
          graphics2d.drawString(text.text, text.x, text.y);
        }
      }

      graphics2d.draw(bs.shape);

      //this part makes a shape fill with a random colour defined in colors array
      graphics2d.setPaint(colors[(colorIndex++) % 4]);
      graphics2d.fill(bs.shape);
    }

    if (startDrag != null && endDrag != null) 
    {
      //sets colour and thickness of the dragging visual feedback
      graphics2d.setPaint(Color.black);
      graphics2d.setStroke(new BasicStroke(5));
      int x = startDrag.x;
      int y = startDrag.y;
      int w = endDrag.x;
      int h = endDrag.y;
      Shape r = new Rectangle2D.Float();

      if (shapeType == "Select" && shapeValue != -1) 
      {
        BaseShape bs = shapes.get(shapeValue);
        x = endDrag.x;
        y = endDrag.y;
        w = bs.getWidth();
        h = bs.getHeight();

        if (bs instanceof Rectangle) 
        {
          r = new Rectangle2D.Float(x, y, w, h);
        }
         else if (bs instanceof Circle) 
         {
          r = new Ellipse2D.Float(x, y, w, h);
        }
      }

      if (shapeType == "Resize" && shapeValue != -1) 
      {
        BaseShape bs = shapes.get(shapeValue);
        x = bs.getX();
        y = bs.getY();
        
        if (bs instanceof Rectangle) 
        {
          r = new Rectangle2D.Float(Math.min(x, w), Math.min(y, h), Math.abs(x - w), Math.abs(y - h));
        } 

        else if (bs instanceof Circle) 
        {
          r = new Ellipse2D.Float(Math.min(x, w), Math.min(y, h), Math.abs(x - w), Math.abs(y - h));
        }
      }

      if (shapeType == "Oval") 
      {
        r = new Ellipse2D.Float(Math.min(x, w), Math.min(y, h), Math.abs(x - w), Math.abs(y - h));
      }

       else if (shapeType == "Rectangle") 
      {
        r = new Rectangle2D.Float(Math.min(x, w), Math.min(y, h), Math.abs(x - w), Math.abs(y - h));
      } 

      else if (shapeType == "Group") //some issues here (dunno if its exactly here)
      {
        r = new Rectangle2D.Float(Math.min(x, w), Math.min(y, h), Math.abs(x - w), Math.abs(y - h));
      } 

      else if (shapeType == "Ungroup") 
      {
        r = new Rectangle2D.Float(Math.min(x, w), Math.min(y, h), Math.abs(x - w), Math.abs(y - h));
      }
      
      graphics2d.draw(r);
    }
  }

  //Create a group based on the selected shape
  //might be a problem here but unsure where
  public void group(int ShapeID, List<BaseShape> shape)
  {
    BaseShape bs = shapes.get(ShapeID);
    for (BaseShape baseShape : shape)
      bs.CreateGroup(baseShape);
    actions.saveAction();
  }

  //Removes the shapes from the group
  public void ungroup(int ShapeID)
  {
    BaseShape bs = shapes.get(ShapeID);
    if(bs.GetGroup() != null)
    {
      for (BaseShape baseShape : bs.GetGroup())
        bs.GetGroup().remove(baseShape);
    }
  }
  //the moveing of groups has some wierd errors mostly when loading multiple shapes
  //Move shape or group of shapes to a new location
  private void DragObject(int val, Point mouseEvent) 
  {
    actions.saveAction();
    availableShapes.clear();
    BaseShape bs = shapes.get(val);

    oldSX = bs.getX();
    oldSY = bs.getY();
    if(bs.GetGroup() != null)
    {
      bs.move(mouseEvent.x, mouseEvent.y);
      for(BaseShape baseShape : bs.GetGroup())
      {
        baseShape.move(baseShape.getX() + (mouseEvent.x - oldSX), baseShape.getY() + (mouseEvent.y - oldSY));
      }
    }
    
    else if (bs.GetGroup() == null)
    {
      for (BaseShape bs2 : shapes)
      {
        if(bs2.GetGroup() != null)
        {
          if(bs2.GetGroup().contains(bs) && bs2.GetGroup() != null)
          {
            bs2.move(bs2.getX() + (mouseEvent.x - oldSX), bs2.getY() + (mouseEvent.y - oldSY));
            for(BaseShape baseShape : bs2.GetGroup())
              baseShape.move(baseShape.getX() + (mouseEvent.x - oldSX), baseShape.getY() + (mouseEvent.y - oldSY));
          }
        } 
        else 
        {
          bs.move(mouseEvent.x, mouseEvent.y);
        }
      }
    }
  }

  //Resize a shape or a group of shapes
  private void ResizeObject(int val, Point point) 
  {
    availableShapes.clear();
    BaseShape bs = shapes.get(shapeValue);

    actions.saveAction();
    oldSX = bs.getX();
    oldSY = bs.getY();

    if(bs.GetGroup() != null)
    {
      bs.resize(endDrag.x, endDrag.y);

      for(BaseShape baseShape : bs.GetGroup())
      {
        baseShape.resize(baseShape.getX() + (endDrag.x - oldSX), baseShape.getY() + (endDrag.y - oldSY));
      }
    }
    else if (bs.GetGroup() == null)
    {
      for (BaseShape x : shapes)
      {
        if(x.GetGroup() != null)
        {
          if(x.GetGroup().contains(bs) && x.GetGroup() != null)
          {

            x.resize(x.getX() + (endDrag.x - oldSX), x.getY() + (endDrag.y - oldSY));

            for(BaseShape baseShape : x.GetGroup())
            {
              baseShape.resize(baseShape.getX() + (endDrag.x - oldSX), baseShape.getY() + (endDrag.y - oldSY));
            }              
          }
        }
        
         else 
        {
          bs.resize(endDrag.x, endDrag.y);
        }
      }
    }
  }
}