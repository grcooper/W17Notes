import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

public class TestLine extends JPanel{
	
	int pos_x = 0;
	int pos_y = 0;
	boolean color1 = false;
	boolean color2 = false;
	
	int[] l1xvals = {25, 600};
	int[] l1yvals = {100, 195};
	int[] l2xvals = {100, 375};
	int[] l2yvals = {225, 425};
	
	public TestLine(){
		this.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				pos_x = e.getX();
				pos_y = e.getY();
				testContainment();
				repaint();
			}
		});
	}
	
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;             // cast to get 2D drawing methods
		g2.setStroke(new BasicStroke(2)); 

		if (color1) {
			g2.setColor(Color.RED);                    // make it blue
		} else {
			g2.setColor(Color.BLUE);
		}
        g2.drawLine(l1xvals[0], l1yvals[0], l1xvals[1], l1yvals[1]); // draw line

 		if (color2) {
			g2.setColor(Color.RED);                    // make it blue
 		} else {
			g2.setColor(Color.BLUE);
 		}
        g2.drawLine(l2xvals[0], l2yvals[0], l2xvals[1], l2yvals[1]); // draw line
	}
	
	protected void testContainment(){
		double d1 = Line2D.ptSegDist(
			l1xvals[0], l1yvals[0], 
			l1xvals[1], l1yvals[1], 
			pos_x, pos_y);
		color1 = (d1 < 5.0); // 5 pixels distance

		double d2 = Line2D.ptSegDist(l2xvals[0], l2yvals[0], l2xvals[1], l2yvals[1], pos_x, pos_y);
		color2 = (d2 < 5.0);
	}
	
	public static void main(String args[]){
		JFrame window = new JFrame("Test Containment");
		window.setSize(800, 600);
		window.setContentPane(new TestLine());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
}