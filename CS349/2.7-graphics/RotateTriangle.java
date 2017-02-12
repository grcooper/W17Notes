/*
* CS 349 Java Code Examples
*
* RotateTriangle1    Animates a triangle rotating around a circle 
					 using transformations.
*
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class RotateTriangle {
	public static int windowSize = 400;
	
	public static void main(String[] args) {
		Canvas canvas = new Canvas();
		JFrame f = new JFrame("Rotate Triangle V 1");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(RotateTriangle.windowSize, RotateTriangle.windowSize);
		f.setContentPane(canvas);
		f.setVisible(true);
	}

}

class Canvas extends JComponent {
	private int fps = 40;
	private double radians_per_second = 1.0;
	private double radius = 0.3*RotateTriangle.windowSize;
	private int x_center = RotateTriangle.windowSize/2;
	private int y_center = RotateTriangle.windowSize/2;
	private int x, y;
	private double cur_angle = 0.0;
	int[] xpoints = {20,0,0};
	int[] ypoints = {0, -10, 10};
	private Polygon triangle = new Polygon(xpoints, ypoints, 3);
	private Timer t;
	
	public Canvas(){
		super();
		ActionListener repainter = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				cur_angle += (radians_per_second/((double)fps));
				repaint();
			}
		};
		t = new Timer(1000/fps, repainter);
		t.start();
	}

	public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;

			g2.translate(x_center, y_center);
			g2.rotate(cur_angle);

			g2.translate(radius, 0);
			g2.rotate(-cur_angle);

			g2.setColor(Color.RED);
			g2.fillPolygon(triangle);
			g2.setColor(Color.BLACK);
			g2.drawPolygon(triangle);
	}
}
