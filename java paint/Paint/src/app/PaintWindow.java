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

import javax.swing.*;
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

  //Basiccly helps me to make sure only 1 action takes place at the time
  private int shapeValue = -1;

  private Shape draggingShape;
  private boolean dragging = false;

  //Sets a vairable set to the frame instance <<Singleton Pattern>>
  private PaintInterface frame = PaintInterface.getInstance();

  //Sets a vairable set to the ShapeActions instance <<Singleton Pattern>>
  private ShapeActions actions = ShapeActions.getInstance();

  private int oldSX;
  private int oldSY;

  public String shapeType = frame.getShapeType();
  public ArrayList<BaseShape> shapes = new ArrayList<BaseShape>();

  private ArrayList<BaseShape> availableShapes = new ArrayList<BaseShape>();

  //Returns the paintsurface instance <<Singleton Pattern>>
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
          for (BaseShape s : shapes) 
          {
            if (s.shape.contains(clickedPoint.x, clickedPoint.y)) 
            {
              actions.saveAction();
              String text = JOptionPane.showInputDialog("Enter text");
              s.addText(shapeType, text);
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
          IShapes rectangle = new Decorator(new Rectangle());
          rectangle.draw(startDrag.x, startDrag.y, mouseEvent.getX(), mouseEvent.getY());
        }
        
        else if (shapeType == "Oval") 
        {
          IShapes oval = new Decorator(new Circle());
          oval.draw(startDrag.x, startDrag.y, mouseEvent.getX(), mouseEvent.getY());
        }
        else if(shapeType == "Group")
        {
          //here declare the number i use to iterate the group (first will be group 1 than 2 etc)
          int sCount = -1;

          //here i make the base for the new group
          List<BaseShape> newGroup = new ArrayList<BaseShape>();
          
          for (BaseShape s : shapes)
          {
            sCount++;
            //somewhere here is a problem wih detecting shapes!
            if (s.shape.contains(clickedPoint.x, clickedPoint.y))
            {
              for (BaseShape x : shapes)
              {
                //this should detect all shapes inside the dragged 
                if(x.getX() >= startDrag.x && x.getY() >= startDrag.y && x.getWidth() <= endDrag.x && x.getHeight() <= endDrag.y)
                {
                    if(s != x)
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
          for (BaseShape s : shapes)
          {
            sCount++;
            if(s.shape.contains(clickedPoint.x, clickedPoint.y) && s.GetGroup() != null)
            {
              ungroup(sCount);
            }
            
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

    //Resize or move the shape to a new location based on the position the mouse is dragged to
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
            BaseShape s = shapes.get(shapeValue);

            startDrag.x = s.getWidth();
            startDrag.y = s.getHeight();
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

  //Create a group based on the selected shape
  public void group(int ShapeID, List<BaseShape> shape)
  {
    BaseShape s = shapes.get(ShapeID);
    for (BaseShape b : shape)
      s.CreateGroup(b);
    actions.saveAction();
  }

  //Removes the shapes from the group
  public void ungroup(int ShapeID)
  {
    BaseShape s = shapes.get(ShapeID);
    if(s.GetGroup() != null)
    {
      for (BaseShape b : s.GetGroup())
        s.GetGroup().remove(b);
    }
  }

  //Move shape or group of shapes to a new location
  private void DragObject(int val, Point mouseEvent) 
  {
    actions.saveAction();
    availableShapes.clear();
    BaseShape s = shapes.get(val);

    oldSX = s.getX();
    oldSY = s.getY();
    if(s.GetGroup() != null)
    {
      s.move(mouseEvent.x, mouseEvent.y);
      for(BaseShape b : s.GetGroup())
      {
        b.move(b.getX() + (mouseEvent.x - oldSX), b.getY() + (mouseEvent.y - oldSY));
      }
    }
    
    else if (s.GetGroup() == null)
    {
      for (BaseShape x : shapes)
      {
        if(x.GetGroup() != null)
        {
          if(x.GetGroup().contains(s) && x.GetGroup() != null)
          {
            x.move(x.getX() + (mouseEvent.x - oldSX), x.getY() + (mouseEvent.y - oldSY));
            for(BaseShape t : x.GetGroup())
              t.move(t.getX() + (mouseEvent.x - oldSX), t.getY() + (mouseEvent.y - oldSY));
          }
        } 

        else 
        {
          s.move(mouseEvent.x, mouseEvent.y);
        }
      }
    }
  }

  //Resize a shape or a group of shapes
  private void ResizeObject(int val, Point point) 
  {
    availableShapes.clear();
    BaseShape s = shapes.get(shapeValue);

    actions.saveAction();
    oldSX = s.getX();
    oldSY = s.getY();

    if(s.GetGroup() != null)
    {
      s.resize(endDrag.x, endDrag.y);

      for(BaseShape b : s.GetGroup())
      {
        b.resize(b.getX() + (endDrag.x - oldSX), b.getY() + (endDrag.y - oldSY));
      }
    }
    else if (s.GetGroup() == null)
    {
      for (BaseShape x : shapes)
      {
        if(x.GetGroup() != null)
        {
          if(x.GetGroup().contains(s) && x.GetGroup() != null)
          {
            x.resize(x.getX() + (endDrag.x - oldSX), x.getY() + (endDrag.y - oldSY));

            for(BaseShape t : x.GetGroup())
            {
              t.resize(t.getX() + (endDrag.x - oldSX), t.getY() + (endDrag.y - oldSY));
            }              
          }
        }
        
         else 
        {
          s.resize(endDrag.x, endDrag.y);
        }
      }
    }
  }

  //Generates the raster type background formation
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
  public void paint(Graphics g) 
  {
    if (shapeType != "Resize" || shapeType == "Select") 
    {
      availableShapes.clear();
    }

    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    paintBackground(g2d);

    //define colours
    Color[] colors = {Color.GREEN, Color.BLACK, Color.RED, Color.BLUE };
    //define color index
    int colorIndex = 0;

    g2d.setStroke(new BasicStroke(2));
    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.50f));
    for (BaseShape s : shapes) 
    {
      if (startDrag != null && s != null && shapeType == "Resize" || shapeType == "Select") 
      {
        if (s.shape.contains(clickedPoint.x, clickedPoint.y)) 
        {
          if (!availableShapes.contains(s)) 
          {
            availableShapes.add(s);
          }
          shapeValue = shapes.indexOf(s);
          g2d.setStroke(new BasicStroke(10));
        }
      }

      // this sets the text font/size 
      g2d.setFont(new Font("Georgia", Font.BOLD, 20));
      g2d.setPaint(Color.BLACK);
      if (s.textList.size() != 0) 
      {
        for (Text text : s.textList) 
        {
          g2d.drawString(text.text, text.x, text.y);
        }
      }

      g2d.draw(s.shape);

      //this part makes a shape fill with a random colour defined in colors array
      g2d.setPaint(colors[(colorIndex++) % 4]);
      g2d.fill(s.shape);
    }

    if (startDrag != null && endDrag != null) 
    {
      //sets colour and thickness of the dragging visual feedback
      g2d.setPaint(Color.black);
      g2d.setStroke(new BasicStroke(5));
      int x = startDrag.x;
      int y = startDrag.y;
      int w = endDrag.x;
      int h = endDrag.y;
      Shape r = new Rectangle2D.Float();

      if (shapeType == "Select" && shapeValue != -1) 
      {
        BaseShape s = shapes.get(shapeValue);
        x = endDrag.x;
        y = endDrag.y;
        w = s.getWidth();
        h = s.getHeight();

        if (s instanceof Rectangle) 
        {
          r = new Rectangle2D.Float(x, y, w, h);
        }
         else if (s instanceof Circle) 
         {
          r = new Ellipse2D.Float(x, y, w, h);
        }
      }

      if (shapeType == "Resize" && shapeValue != -1) 
      {
        BaseShape s = shapes.get(shapeValue);
        x = s.getX();
        y = s.getY();
        
        if (s instanceof Rectangle) 
        {
          r = new Rectangle2D.Float(Math.min(x, w), Math.min(y, h), Math.abs(x - w), Math.abs(y - h));
        } 

        else if (s instanceof Circle) 
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
      
      g2d.draw(r);
    }
  }

}