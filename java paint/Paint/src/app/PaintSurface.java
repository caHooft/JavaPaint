package app;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//main window of the application
public class PaintSurface extends JFrame implements ActionListener
{  
    private static PaintSurface paintSurface= new PaintSurface(); 
    
    public static PaintSurface getInstance()
    {
        return paintSurface;
    }

    private PaintSurface()
    {
        //set up the app
        setLayout(new BorderLayout());
        JPanel panel = new JPanel(new FlowLayout());

        addButton(new JButton("Rectangle"), panel);
        addButton(new JButton("Circle"), panel);        
        addButton(new JButton("Text"), panel);
        addButton(new JButton("Select"), panel);        
        addButton(new JButton("Move"), panel);
        addButton(new JButton("Resize"), panel);

        this.add(panel, BorderLayout.NORTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        this.setSize(900, 600);
    } 

    private void addButton(JButton btn, JPanel panel){
        btn.addActionListener(this);
        panel.add(btn);
    }

    public void actionPerformed(ActionEvent ae)
    {
        String name = ae.getActionCommand().toString();
    }     
}
    
  