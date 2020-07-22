package app;
import java.awt.*; 
import java.util.ArrayList;
import java.awt.geom.* ; 
import java.awt.Dialog.*;
import java.awt.event.* ; 
import javax.swing.* ; 
import javax.swing.JOptionPane;

import java.util.* ; 

//Drawpanel is the drawing portion of the window
public class DrawPanel extends JComponent 
{
    private static DrawPanel drawPanel = new DrawPanel(); 

    public enum ShapeType
    {
        Rectangle,
        Circle,
        None,
    }

    
    public String shapeType = "Rectangle";
    public ArrayList<Shape> shapes = new ArrayList<Shape>();
    
    String[] textPos = new String[] { "Top", "Bottom", "Left", "Right" };

    //creates instance draw panel
    public static DrawPanel getInstance()
    {
        return drawPanel; 
    }

    //mousedrag
    private Point startDrag, endDrag;

    public ShapeType currentType = ShapeType.None; 

    private DrawPanel()
    {
        // doet muisdingen
        this.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
                startDrag = new Point(e.getX(),e.getY());
            }

            public void mouseReleased(MouseEvent e)
            {                
                repaint(); 
            }
        }); 
            
        this.addMouseMotionListener(new MouseMotionAdapter() 
        {
            public void mouseDragged(MouseEvent e) 
            {
              endDrag = new Point(e.getX(), e.getY());
              repaint();              
            }
        });
    }

    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g; 
        paintBackground(g2); 
 
        g2.setStroke(new BasicStroke(15)); 
        
        paintPendingOutline(g2);

        paintBorder(g2); 
    }

    private void paintBackground(Graphics2D g2)
    {
        float width = getSize().width; 
        float height = getSize().height; 

        g2.setPaint(Color.GRAY); 
        g2.setStroke(new BasicStroke(1)); 
        for (int i = 0; i < width; i += 20)
        {
            Shape line = new Line2D.Float(i, 0, i, height); 
            g2.draw(line); 
        }
        for (int i = 0; i < height; i += 20)
        {
            Shape line = new Line2D.Float(0, i, width, i); 
            g2.draw(line); 
        }

    }
    
    private void paintPendingOutline(Graphics2D g2)
    {
        if (startDrag != null && currentType != ShapeType.None) 
        {
            g2.setPaint(Color.LIGHT_GRAY);
            g2.setStroke(new BasicStroke(10));
            int x = startDrag.x;
            int y = startDrag.y;
            int w = endDrag.x;
            int h = endDrag.y;
            Shape r = new Rectangle2D.Float();
            
            g2.draw(r);
          }
    }
}