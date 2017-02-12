/*
* CS 349 Java Code Examples
*
* TransConDemo    Demo of different concatenation orders of matrix transforms.
					 Click the window to change the order.
*
*/
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.JButton;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import javax.vecmath.*;
import java.lang.Math.*;
import java.util.Random;
import java.awt.event.*;

// create the window and run the demo
public class TransConDemo {
	
    public static void main(String[] args) {
        // create the window        
    	Canvas canvas = new Canvas();
        JFrame f = new JFrame("TransConDemo"); // jframe is the app window
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(400, 400); // window size
        f.setContentPane(canvas); // add canvas to jframe         
        f.setVisible(true); // show the window
    }
} 

class Canvas extends JComponent {
	
	// the house shape
	private MyShape shape;
	// a larger font for displaying the concatenation order
	private Font font = new Font("SansSerif", Font.PLAIN, 30);
	// the concatenation order
    private int order = 0;

	
	Canvas()
	{
		shape = new MyShape();
		shape.setPoints(new double[][] { 
				{ 0, 0 } , {100, 0}, { 100, 100 }, { 50, 150 }, { 0, 100 }
		} );
		shape.setIsClosed(true);
		shape.setIsFilled(false);
		
		// using a mouse adapter with an anonymous class to get
		// only mouse clicked events
		addMouseListener(new MouseAdapter() { 
	          public void mouseClicked(MouseEvent me) { 
	        	  order = (order + 1) % 6;
	        	  repaint();
	          } 
	        }); 
		
		System.out.println("click to change transformation order");
	}
    
    // custom graphics drawing 
    public void paintComponent(Graphics g) {
    	super.paintComponent(g); // JPanel paint
    	Graphics2D g2 = (Graphics2D)g;
    	
    	// antialiasing
    	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
    	        RenderingHints.VALUE_ANTIALIAS_ON);
    	
    	// save the current transform matrix 
    	AffineTransform M = g2.getTransform();
    	
    	// you can move (0,0) to the bottom left using these
    	g2.translate(0, getHeight());
    	g2.scale(1, -1);
    	
    	// draw the original shape in "model" coordinates
    	shape.setColour(Color.BLACK);
    	shape.paint(g2);
    	
    	// create transformation matrices
    	AffineTransform R = AffineTransform.getRotateInstance(Math.toRadians(30));
    	AffineTransform T = AffineTransform.getTranslateInstance(100, 0);
    	AffineTransform S = AffineTransform.getScaleInstance(2, 1.2);
    	

        
    	// concatenate the matrices in 1 of 6 orders
    	String s = "p'=";
    	switch (order)
    	{
    	case 0:
	    	s += "TRS";
	    	g2.transform(T);
	    	g2.transform(R);
	    	g2.transform(S);
	    	break;	
	    	
    	case 1:
	    	s += "TSR";
	    	g2.transform(T);
	    	g2.transform(S);
	    	g2.transform(R);
	    	break;
	    	
    	case 2:
	    	s += "RST";
	    	g2.transform(R);
	    	g2.transform(S);
	    	g2.transform(T);
	    	break;		

    	case 3:
	    	s += "RTS";
	    	g2.transform(R);
	    	g2.transform(T);
	    	g2.transform(S);
	    	break;
	    	
    	case 4:
	    	s += "SRT";
	    	g2.transform(S);
	    	g2.transform(R);
	    	g2.transform(T);
	    	break;	
	    	
    	case 5:
	    	s += "STR";
	    	g2.transform(S);
	    	g2.transform(T);
	    	g2.transform(R);
	    	break;		    	
    	}
    	s += "p";
    	
    	// the shape will get transformed into "world" coordinates
    	shape.setColour(Color.RED);   	
    	shape.paint(g2);
    	
    	// reset to transform before we did the T, R, and S
    	g2.setTransform(M);
    	
    	// display the order text
    	g2.setColor(Color.BLACK);
    	g2.setFont(font);
    	g2.drawString(s, getWidth() / 3, getHeight() - 40);

    }
}
