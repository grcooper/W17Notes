import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

public class TestClick extends JPanel{

	int pos_x = 0;
	int pos_y = 0;
	boolean color = false;

//	Point2D[] myPoints = new Point2D[4];
	Polygon myPoly = new Polygon();

	public TestClick(){
		this.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				pos_x = e.getX();
				pos_y = e.getY();
				testContainment();
				repaint();
			}
		});
		myPoly.addPoint(25, 100);
		myPoly.addPoint(600, 195);
		myPoly.addPoint(375, 425);
		myPoly.addPoint(100, 225);
	}

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;       // cast to get 2D drawing methods
		if (color)
			g2.setColor(Color.BLUE);                // make it blue
		else
			g2.setColor(Color.RED);
        g2.fillPolygon(myPoly); 							// draw line
	}

	protected void testContainment(){
		color=myPoly.contains(pos_x, pos_y);
	}

	public static void main(String args[]){
		JFrame window = new JFrame("Test Containment");
		window.setSize(800, 600);
		window.setContentPane(new TestClick());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
}
