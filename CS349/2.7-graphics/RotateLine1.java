/*
* CS 349 Java Code Examples
*
* RotateLine    Use transformations to rotate line
*
*/
import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.geom.AffineTransform;


public class RotateLine1  {
    public static void main(String[] args) {
        JFrame f = new JFrame("RotateLine1");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(350, 250);
        f.setContentPane(new Canvas());
        f.setVisible(true);
    }
}

class Canvas extends JComponent {
    private static final int x1 = 100;
    private static final int y1 = 150;
    private static final int len = 200;

    private void drawBar(Graphics2D g2) {
        g2.drawLine(x1, y1, x1+len, y1);
        g2.fillOval(x1-15, y1-15, 30, 30);
        g2.fillOval(x1 + len - 15, y1-15, 30, 30);
    }

    private void drawAxes(Graphics2D g2) {
        int w = this.getWidth();
        int h = this.getHeight();
        g2.drawLine(0, 0, 0, h); // vertical axis
        g2.drawLine(0, 0, w, 0);  // horizontal axis

        // Draw grid
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

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(8));
        this.drawAxes(g2);

        g2.setStroke(new BasicStroke(10));
        g2.setColor(Color.RED);
        this.drawBar(g2);

        g2.setColor(Color.GREEN.darker());
        this.drawBar(g2);

    }
}
