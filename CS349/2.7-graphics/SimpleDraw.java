import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.event.*;

public class SimpleDraw {
    public static void main(String[] args) {
        JFrame f = new JFrame("SimpleDraw");    // jframe is the app window
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(400, 400);                    // window size
        f.setContentPane(new Canvas());         // add canvas to jframe
        f.setVisible(true);                     // show the window
    }
}

// JComponent is a base class for custom components
class Canvas extends JComponent {
	
	int pos_x = 0;
	int pos_y = 0;
	
	Canvas(){
		super();
		this.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				pos_x = e.getX();
				pos_y = e.getY();
				repaint();
			}
		});
	}

    // custom graphics drawing
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;             // cast to get 2D drawing methods
        g2.setStroke(new BasicStroke(32));          // 32 pixel thick stroke
        g2.setColor(Color.BLUE);                    // make it blue
        g2.drawLine(0, 0, getWidth(), getHeight()); // draw line
        g2.setColor(Color.RED);
        g2.drawLine(getWidth(), 0, 0, getHeight());
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1));          // 32 pixel thick stroke
        g2.draw(new Star(pos_x, pos_y));
    }
}

class Star extends Path2D.Double {
    public Star(int pos_x, int pos_y) {
        // super(WIND_EVEN_ODD);
        this.moveTo(pos_x, pos_y);
        this.lineTo(pos_x-1.5, pos_y+5);
        this.lineTo(pos_x-7, pos_y+5);
        this.lineTo(pos_x-2.5, pos_y+8);
        this.lineTo(pos_x-4.2, pos_y+13);
        this.lineTo(pos_x+0, pos_y+10);
        this.lineTo(pos_x+4.2, pos_y+13);
        this.lineTo(pos_x+2.5, pos_y+8);
        this.lineTo(pos_x+7, pos_y+5);
        this.lineTo(pos_x+1.5, pos_y+5);
        this.lineTo(pos_x, pos_y);
    }
}