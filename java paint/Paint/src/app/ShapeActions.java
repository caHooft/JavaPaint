package app;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

// used as the "BuyStock" replacement for the Command Pattern
public class ShapeActions
 {
  //singleton pattern instance
  private static ShapeActions instance = new ShapeActions();

  //Create new TextConverter
  private TxtConverter txtConverter = new TxtConverter();

  //Uses the PaintSurface
  public PaintWindow surface;

  //Create a list of actions to undo/redo
  public ArrayList<ArrayList<BaseShape>> undo = new ArrayList<ArrayList<BaseShape>>();
  public ArrayList<ArrayList<BaseShape>> redo = new ArrayList<ArrayList<BaseShape>>();

  //<<singleton object>>
  //Return current instance
  public static ShapeActions getInstance() 
  {
    return instance;
  }

  //Save the action performed
  //not sure why this works/does it?
  public void saveAction() 
  {
    undo.add(clone(surface.shapes));
  }

  //Undo the previous move
  public void undoMove() 
  {
    //here i catch the possibility of there not being anything to undo
    if (undo.size() <= 0)
    {
      return;
    }

    //first i add a redo action for the thing i undo
    redo.add(clone(surface.shapes));

    surface.shapes = clone(undo.get(undo.size() - 1));
    //remove the thing i just undid
    undo.remove(undo.size() - 1);
    surface.repaint();
  }

  //Save the file
  //this is prob fine but needs a look though the problem probebly lies by the selecting
  public void save()
  {
    try 
    {
      txtConverter.SaveShapeToFile(surface.shapes);
    }
     catch (Exception ex)     
    {
      Logger.getLogger(ShapeActions.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  //Load the previous saven file
  //works
  public void load()
  {
    try 
    {
      txtConverter.LoadShapeFromFile();
    } 

    catch (Exception ex)
    {
      Logger.getLogger(ShapeActions.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  //Redo to previous move
  public void redoMove() 
  {
    //catch the possibility of there not being anything to redo
    if (redo.size() <= 0)
    {
      return;
    }

    //first i add a undo action for the thing i redo
    undo.add(clone(surface.shapes));

    surface.shapes = clone(redo.get(redo.size() - 1));
    //remove the thing i redid
    redo.remove(redo.size() - 1);
    surface.repaint();
  }

  //Clear all shapes from the surface
  //works
  public void clear() 
  {
    saveAction();
    surface.shapes.clear();
  }

  //Add shape to the surface
  //not 100% sure
  public void addShapeToArray(BaseShape r) 
  {
    saveAction();
    surface.shapes.add(r);
  }

  public void AddText(BaseShape r) 
  {
    saveAction();
    surface.shapes.add(r);
  }
  
  //Clone all shapes on the surface
  public ArrayList<BaseShape> clone(ArrayList<BaseShape> list) 
  {
    ArrayList<BaseShape> clonedList = new ArrayList<BaseShape>(list.size());

    for (BaseShape shape : list) 
    {
      clonedList.add(shape.clone());
    }
    
    return clonedList;
  }
}