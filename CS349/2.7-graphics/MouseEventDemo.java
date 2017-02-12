/*
* CS 349 Java Code Examples
*
* MouseEventDemo    Mouse listeners and mouse motion listeners.
*
*/

import javax.swing.JFrame;
import javax.swing.JComponent;
import java.awt.event.*;
import java.awt.*;


public class MouseEventDemo {
    public static void main(String[] args) {
        JFrame f = new JFrame("MouseEventDemo"); // jframe is the app window
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(400, 400); // window size
        f.setContentPane(new Canvas()); // add canvas to jframe    
        f.setVisible(true); // show the window
    }
}

class Canvas extends JComponent {

    private Color colour = Color.GRAY;
    private int x;
    private int y;
    private int size = 50;

    Canvas() {
        // add listeners
        this.addMouseListener(new MListener());
        this.addMouseMotionListener(new MMListener());
    }

    // custom graphics drawing
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(colour);
        g2.fillOval(this.x - this.size / 2, this.y - this.size / 2, this.size, this.size);
    }

    private class MListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent arg0) {
            System.out.format("click %d,%d count %d\n", arg0.getX(), arg0.getY()
                    , arg0.getClickCount());
            if (arg0.getClickCount() == 2) // double click
                Canvas.this.colour = Color.PINK;
            else
                Canvas.this.colour = Color.GREEN;
            Canvas.this.repaint();
        }

        @Override
        public void mouseEntered(MouseEvent arg0) {
            System.out.format("enter %d,%d\n", arg0.getX(), arg0.getY());
            Canvas.this.colour = Color.BLACK;
            Canvas.this.repaint();
        }

        @Override
        public void mouseExited(MouseEvent arg0) {
            System.out.format("exit %d,%d\n", arg0.getX(), arg0.getY());
            colour = Color.GRAY;
            repaint();
        }

        @Override
        public void mousePressed(MouseEvent arg0) {
            System.out.format("press %d,%d\n", arg0.getX(), arg0.getY());
            colour = Color.RED;
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent arg0) {
            System.out.format("release %d,%d\n", arg0.getX(), arg0.getY());
            colour = Color.BLUE;
            repaint();
        }
    }

    private class MMListener implements MouseMotionListener {
        @Override
        public void mouseDragged(MouseEvent arg0) {
            System.out.format("drag %d,%d\n", arg0.getX(), arg0.getY());
            x = arg0.getX();
            y = arg0.getY();
            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent arg0) {
            System.out.format("move %d,%d\n", arg0.getX(), arg0.getY());
            x = arg0.getX();
            y = arg0.getY();
            repaint();
        }
    }

}