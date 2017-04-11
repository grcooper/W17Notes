import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class CopyCutPasteDemo extends JPanel implements ClipboardOwner {
    private JTextArea textArea;
    
    public CopyCutPasteDemo() {
        
        this.setLayout(new FlowLayout());

        // Create a text area for copy and paste tests
        // Note that by default, text widgets will support keyboard shortcuts
        // for copy/paste
        textArea = new JTextArea();
        textArea.setMinimumSize(new Dimension(300, 300));
        textArea.setPreferredSize(textArea.getMinimumSize());
        this.add(textArea);
        
        
        // Create some copy/cut/paste buttons to support manual copying and pasting
        JButton copyButton = new JButton("Copy Text");
        JButton cutButton = new JButton("Cut Text");
        JButton pasteButton = new JButton("Paste Text");
        this.add(copyButton);
        this.add(cutButton);
        this.add(pasteButton);
        
        
        // Add action listeners for actually performing the copy/paste
        copyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doCopy();
            }
        });

        cutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doCut();
            }
        });

        pasteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doPaste();
            }
        });
    }
    
    private void doCopy() {
        
        // Get the system clipboard
        Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        
        // Create a transferable object encapsulating all the info for the copy
        Transferable transferObject = new Transferable() {
            private String s = textArea.getSelectedText();
            
            // Returns the copy data
            public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException ,IOException {
                if (flavor.equals(DataFlavor.stringFlavor)) {
                    return s;
                }
                throw new UnsupportedFlavorException(flavor);
            }
            
            // Returns the set of data formats we can provide
            public DataFlavor[] getTransferDataFlavors() {
                return new DataFlavor[] { DataFlavor.stringFlavor };
            }
            
            // Indicates whether we can provide data in the specified format
            public boolean isDataFlavorSupported(DataFlavor flavor) {
                return flavor.equals(DataFlavor.stringFlavor);
            }
        };
        
        // Now set the contents of the clipboard to our transferable object
        // NOTE: The second argument "this" tells the system that this 
        //       object would like to be the owner of the clipboard.
        //       As such, this object must implement the ClipboardOwner interface 
        systemClipboard.setContents(transferObject, this);
    }

    private void doCut() {
        // Most of cut is copy!
        doCopy();
        textArea.replaceSelection("");
    }
    
    private void doPaste() {
        
        // Grab system clipboard
        Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        
        // For our own edification, print out the data formats available on the clipboard
        for (DataFlavor flavor : systemClipboard.getAvailableDataFlavors()) {
            System.out.println("Flavor: " + flavor);
        }
        
        // Check if we can get the data as a string
        if (systemClipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
            try {
                // Grab the data, set our text area to the data
                String theText = (String)systemClipboard.getData(DataFlavor.stringFlavor);
                textArea.replaceSelection(theText);
            } catch (UnsupportedFlavorException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Implement the ClipboardOwner interface
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        System.out.println("Lost ownership of clipboard");
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Clipboard demo");
        f.getContentPane().add(new CopyCutPasteDemo());
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.pack();
        f.setVisible(true);
    }
}
