/*
* CS 349 Java Code Examples
*
* ShapeDemo    Demo of MyShape class: draw shapes using mouse.
*
*/
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import javax.vecmath.*;
import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

// create the window and run the demo
public class ShapeDemo2 extends JPanel implements MouseInputListener {

    MyShape shape;

    ShapeDemo2() {
        // add listeners
        addMouseListener(this);
        addMouseMotionListener(this);  
    }

    public static void main(String[] args) {
        // create the window         
        ShapeDemo2 canvas = new ShapeDemo2();
        JFrame f = new JFrame("ShapeDemo2"); // jframe is the app window
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(400, 400); // window size
        f.setContentPane(canvas); // add canvas to jframe
        f.setVisible(true); // show the window
    }
    // custom graphics drawing 
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; // cast to get 2D drawing methods
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  // antialiasing look nicer
                            RenderingHints.VALUE_ANTIALIAS_ON);
        if (shape != null)
            shape.paint(g2);
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        shape = new MyShape();
        shape.setIsClosed(false);
        shape.setIsFilled(false);
        shape.setColour(Color.BLUE);
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
    }

    @Override
    public void mouseDragged(MouseEvent arg0) {
        shape.addPoint(arg0.getX(), arg0.getY());
        repaint();      
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
    }
}

