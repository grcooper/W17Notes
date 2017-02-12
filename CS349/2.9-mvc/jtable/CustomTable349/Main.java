
/*
 *	This example is based on CustomTable.java from Oracle but rewritten
 *  to more explicitly show the MVC structure expected in CS349.
 * 
 *  @author Byron Weber Becker
 */

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
    	CustomTableModel model = new CustomTableModel();
        //Create and set up the content pane.
        CustomTableView newContentPane = new CustomTableView(model);

        //Create and set up the window.
        JFrame frame = new JFrame("CustomTableCS349");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}