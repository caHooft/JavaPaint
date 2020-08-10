package app;

import java.awt.*; 
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Shape;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
 
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JComboBox;


//Create the frame
public class PaintInterface extends JFrame implements ActionListener 
{
  //Singleton pattern is in use here because we only want ONE instance
  //we also need global acces to this object
  //we only want one instance because multiple would be confusing for the user and the producer
  private static PaintInterface instance = new PaintInterface();

  private PaintWindow surface;

  //Get the Command instance Singleton Pattern
  //I used singleton pattern for the commands
  //We only want one instance of commands
  //We need to commands to be global accesable
  private Command command = Command.getInstance();

  //Get the ShapeActions instance Singleton Pattern
  //I used this pattern here because i only want 1 instance of Shapeactions
  //It also needs to be globally available
  private ShapeActions actions = ShapeActions.getInstance();
  private VisiorCommand sText = new VisiorCommand(actions);
  private RedoCommand sRedo = new RedoCommand(actions);
  private ClearCommand sClear = new ClearCommand(actions);
  private SaveCommand sSave = new SaveCommand(actions);
  private LoadCommand sLoad = new LoadCommand(actions);

  //putting this seperate for now.
  //want to map this to the last commands method of unExecute
  private UndoCommand sUndo = new UndoCommand(actions);

  public String shapeType;
  public ArrayList<Shape> shapes = new ArrayList<Shape>();

  String[] textPos = new String[] { "Top", "Left", "Bottom", "Right"};

  JComboBox<String> positionList = new JComboBox<>(textPos);
  JComboBox<String> textList = new JComboBox<>(textPos);

  //singleton pattern return current instance
  public static PaintInterface getInstance() 
  {
    return instance;
  }
    //returns the shapeType
    public String getShapeType() 
    {
      return shapeType;
    }
    
    //returns all the shapes on the frame
    public ArrayList<Shape> getShapes() 
    {
  
      return shapes;
    }

  //Create all objects in the frame (buttons/layout)
  public void createDrawObj() 
  {
    //Gets paintSurface instance 
    surface = PaintWindow.getInstance();
    actions.surface = surface;

    ButtonGroup buttonGroup = new ButtonGroup();

    //create buttons for actions
    JRadioButton ovalButton = new JRadioButton("Oval");
    JRadioButton rectangleButton = new JRadioButton("Rectangle");
    JButton textButton = new JButton("Text");
    JRadioButton selectButton = new JRadioButton("Select");
    JRadioButton resizeButton = new JRadioButton("Resize");
    JButton clearButton = new JButton("Clear");
    JButton undoButton = new JButton("Undo");
    JButton redoButton = new JButton("Redo");
    JRadioButton groupButton = new JRadioButton("Group");
    JRadioButton ungroupButton = new JRadioButton("Ungroup");
    JButton loadButton = new JButton("load");
    JButton saveButton = new JButton("Save");    
    JPanel radioPanel = new JPanel(new FlowLayout());

    //here the order of the buttons/use 
    CreateObjectInFrame(buttonGroup, radioPanel, ovalButton);
    CreateObjectInFrame(buttonGroup, radioPanel, rectangleButton);
    CreateObjectInFrame(buttonGroup, radioPanel, textButton);
    CreateObjectInFrame(buttonGroup, radioPanel, textList);
    CreateObjectInFrame(buttonGroup, radioPanel, selectButton);
    CreateObjectInFrame(buttonGroup, radioPanel, resizeButton);
    CreateObjectInFrame(buttonGroup, radioPanel, groupButton);
    CreateObjectInFrame(buttonGroup, radioPanel, ungroupButton);
    CreateObjectInFrame(buttonGroup, radioPanel, clearButton);
    CreateObjectInFrame(buttonGroup, radioPanel, undoButton);
    CreateObjectInFrame(buttonGroup, radioPanel, redoButton);
    CreateObjectInFrame(buttonGroup, radioPanel, loadButton);
    CreateObjectInFrame(buttonGroup, radioPanel, saveButton);

    //Here i set the layour/size and the layout of the app    
    this.setLayout(new BorderLayout());
    this.setFont(new Font("Georgia", Font.BOLD, 20));
    this.setSize(1024, 768);
    this.add(radioPanel, BorderLayout.NORTH);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.add(surface, BorderLayout.CENTER);
    this.setVisible(true);
  }

  //Add the buttons to the frame
  private void CreateObjectInFrame(ButtonGroup group, JPanel panel, Component component) 
  {
    panel.add(component);

    if (component instanceof JButton) 
    {
      JButton b = (JButton) component;
      group.add(b);
      b.addActionListener(this);
    }
    
    else if (component instanceof JRadioButton) 
    {
      JRadioButton b = (JRadioButton) component;
      group.add(b);
      b.addActionListener(this);
    } 

    else if (component instanceof JComboBox) 
    {
      JComboBox<String> b = (JComboBox) component;
      b.addActionListener(this);
    }
  }

  //<<Command Pattern>>
  //
  //Execute the commands based on the button pressed by the user
  //so when the button Clear gets pressed the clear command will be called
  public void actionPerformed(ActionEvent actionEvent) 
  {
    if (actionEvent.getActionCommand().toString() == "Clear") 
    {
      command.addCommand(sClear);
      command.executeCommand();
    } 

    else if (actionEvent.getActionCommand().toString() == "Text") 
    {
      //old before command pattern style text stuff
      /*
      String n = (String)JOptionPane.showInputDialog(null, "Select a position","", JOptionPane.QUESTION_MESSAGE, icon, textPos, textPos[2]);
      String m = JOptionPane.showInputDialog("Enter Text", 42);
      */   
      //String n = (String)JOptionPane.showInputDialog(null, "Select a position","", JOptionPane.QUESTION_MESSAGE, null, textPos, textPos[2]);
      //String m = JOptionPane.showInputDialog("Enter Text", 42);
      //shapeType = (String) positionList.getSelectedItem();
      //shapeType = (String) textList.getSelectedItem();
      //shapeType = m;
      
      command.addCommand(sText);
      command.executeCommand();

    } 

    else if (actionEvent.getActionCommand().toString() == "Undo") 
    {
      command.addCommand(sUndo);
      command.executeCommand();
    } 

    else if (actionEvent.getActionCommand().toString() == "Redo") 
    {
      command.addCommand(sRedo);
      command.executeCommand();
    } 

    else if (actionEvent.getActionCommand().toString() == "Save") 
    {
      command.addCommand(sSave);
      command.executeCommand();
    } 

    else if (actionEvent.getActionCommand().toString() == "load") 
    {
      command.addCommand(sLoad);
      command.executeCommand();
      surface.repaint();
    }

    else if(actionEvent.getActionCommand().toString() == "comboBoxChanged")
    {
      shapeType = (String) textList.getSelectedItem();
                        
    }

    else 
    {
      System.out.println(actionEvent.getActionCommand().toString());
      shapeType = actionEvent.getActionCommand().toString();
    }
    repaint();
  }
}