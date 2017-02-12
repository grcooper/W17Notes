/*
* CS 349 Java Code Examples
* Jeff Avery
*
* ShapeModel2   
* Uses Graphics context and built-in AffineTransform matrix
* Simpler approach than manipulating points directly
*
*/
import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.geom.AffineTransform;
import java.awt.Point;

public class ShapeModel2  {
    public static void main(String[] args) {
        JFrame f = new JFrame("ShapeModel1");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(350, 250);
        f.setContentPane(new Canvas());
        f.setVisible(true);
    }
}

class Canvas extends JComponent {
    private void drawAxes(Graphics2D g2) {
        int w = this.getWidth();
        int h = this.getHeight();
        g2.drawLine(0, 0, 0, h);    // vertical axis
        g2.drawLine(0, 0, w, 0);    // horizontal axis

        // draw grid
        g2.setStroke(new BasicStroke(1));
        g2.setColor(Color.GRAY);
        
        // horizontal lines
        for(int i = 50; i<h; i+=50) {
            g2.drawLine(0, i, w, i);
        }
        // vertical lines
        for(int i = 50; i<w; i+=50) {
            g2.drawLine(i, 0, i, h);
        }
    }

    class Line {
        // default model properties
        Point p1 = new Point(0, 0);
        Point p2 = new Point(200, 0);
        Color color = Color.RED;
          
        // draw using current context
        private void drawBar(Graphics2D g2) {
            g2.setColor(color);
            g2.setStroke(new BasicStroke(10));
            g2.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
            g2.fillOval((int) p1.getX()-15, (int) p1.getY()-15, 30, 30);
            g2.fillOval((int) p2.getX()-15, (int) p2.getY()-15, 30, 30);
        }
    }

    // canvas paint
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(8));
        this.drawAxes(g2);

        // unmodified shape model
        // translate to starting location
        Line l1 = new Line();
        g2.translate(100,150);
        l1.drawBar(g2);             // draw using defaults

        // shape model rotated around this point
        Line l2 = new Line();
        l2.color = Color.GREEN;
        g2.rotate(-Math.PI/8);      // rotate around point
        l2.drawBar(g2);             // draw it

        // shape model scaled around this point
        Line l3 = new Line();
        l3.color = Color.BLUE;
        g2.rotate(2 * Math.PI/8);   // rotate relative to previous rotation
        g2.scale(0.5, 0.5);         // scale around point
        l3.drawBar(g2);             // draw it        
    }
}
