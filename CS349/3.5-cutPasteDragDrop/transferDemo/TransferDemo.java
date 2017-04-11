
/*
 * This demo started life as part of Sun's Java Tutorial for Drag and Drop/Cut and Paste.
 * The tutorial had already been updated to Java 6 (which includes changes to
 * TransferHandler that I didn't want to use yet), so a cached version was found
 * at http://mockus.us/optimum/JavaTutorial/mustang/uiswing/dnd/examples/index.html#DragPictureDemo
 * (retrieved June 17, 2009).
 * 
 * It was modified by Byron Weber Becker as follows:
 * -- code to initialize the pictures was simplified with an array and loop
 * -- removed unused imports 
 * -- used inner classes for listeners rather than exposing the
 *    methods in a class's public interface
 * -- there were funny inheritance interactions between DTPicture and Picture
 *    in the mouse and mousemotion listeners, so combined the two classes
 * -- added support for the javaFileList data flavor, enabling drag 'n drop
 *    from the desktop and to other applications such as image editors
 * -- removed the copy/cut/paste magic using action maps and the automatic 
 *    stuff in TransferHandler and replaced it with direct and easier to
 *    understand implementations.
 */

import java.awt.*;

import javax.swing.*;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class TransferDemo  {

    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("TransferDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the menu bar and content pane.
        PicturePanel demo = new PicturePanel();
        //demo.setOpaque(true); //content panes must be opaque
        frame.setContentPane(demo);
        
        frame.setJMenuBar(demo.createMenuBar());
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
    	System.out.println("You may need to copy the images directory from the");
    	System.out.println("source code directory to the same directory as TransferDemo.class.");
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
