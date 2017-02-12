/*
* CS 349 Java Code Examples
* Jeff Avery
*
* ShapeModel1    
* Translate the points of a shape model directly
* Demonstrates translating to/from the origin to rotate a shape
*
*/
import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.geom.AffineTransform;
import java.awt.Point;

public class ShapeModel1  {
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
        Point p1 = new Point(100, 150);
        Point p2 = new Point(300, 150);
        Color color = Color.RED;
    
        // use matrices to transform points directly
        // points modified in-place, so transformations are cumulative
        private void rotate(double theta) {
            // AffineTransform matrix = AffineTransform.getRotateInstance(theta);
            // matrix.transform(p1, p1); 
            // matrix.transform(p2, p2);
            p2.x = (int) ((double) p2.x * Math.cos(theta) - p2.y * Math.sin(theta));
            p2.y = (int) ((double) p2.x * Math.sin(theta) + p2.y * Math.cos(theta));
        }

        private void translate(double x, double y) {
            // AffineTransform matrix = AffineTransform.getTranslateInstance(x, y);
            // matrix.transform(p1, p1);
            // matrix.transform(p2, p2);
            p1.x += (int) x;
            p1.y += (int) y;
            p2.x += (int) x;
            p2.y += (int) y;            
        }

        private void scale(double s) {
            // AffineTransform matrix = AffineTransform.getScaleInstance(s, s);
            // matrix.transform(p1, p1);
            // matrix.transform(p2, p2);
            p1.x *= s;
            p1.y *= s;
            p2.x *= s;
            p2.y *= s;
        }
        
        // draw using current points
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
        Line l1 = new Line();
        l1.drawBar(g2);             // just draw using defaults

        // shape model rotated around the origin
        Line l2 = new Line();
        l2.color = Color.GREEN;
        
        double x = l2.p1.getX();    // get current x, y from shape model
        double y = l2.p1.getY();
        l2.translate(-x, -y);       // translate shape to origin
        l2.rotate(-Math.PI/8);      // rotate around the origin
        l2.translate(+x, +y);       // translate back to the original location
        l2.drawBar(g2);             // draw it

        // shape model scaled around the origin
        Line l3 = new Line();
        l3.color = Color.BLUE;
        
        x = l3.p1.getX();           // get current x, y from shape model
        y = l3.p1.getY();
        l3.translate(-x, -y);       // translate shape to origin
        l3.rotate(Math.PI/8);       // rotate opposite direction from previous
        l3.scale(0.5);              // scale around the origin
        l3.translate(+x, +y);       // translate back to the original location
        l3.drawBar(g2);             // draw it        
    }
}
