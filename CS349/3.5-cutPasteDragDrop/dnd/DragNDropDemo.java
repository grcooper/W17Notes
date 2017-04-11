import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTargetEvent;
import java.io.IOException;
import java.io.File;
import java.util.List;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;

public class DragNDropDemo {

    private JLabel imageLabel;
    private Image image;

    public DragNDropDemo() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.imageLabel = new JLabel();

        f.getContentPane().add(imageLabel);

        imageLabel.setTransferHandler(new ImageHandler());

        DragGesture dg = new DragGesture();
        imageLabel.addMouseListener(dg); // For mouseDragged
        imageLabel.addMouseMotionListener(dg); // For mouseReleased

        f.setSize(400, 300);
        f.setVisible(true);
    }

    private class ImageHandler extends TransferHandler {

        protected Transferable createTransferable(JComponent c) {
            System.out.println("Creating Transferable");
            return new Transferable() {
                private Image img = ((ImageIcon)imageLabel.getIcon()).getImage();

                public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
                    if (flavor.equals(DataFlavor.imageFlavor)) {
                        System.err.println("getTransferData: " + image);
                        //return ((ImageIcon)imageLabel.getIcon()).getImage();
                        return this.img;
                    }
                    throw new UnsupportedFlavorException(flavor);
                }

                public DataFlavor[] getTransferDataFlavors() {
                    return new DataFlavor[] { DataFlavor.imageFlavor };
                }

                public boolean isDataFlavorSupported(DataFlavor flavor) {
                    return flavor.equals(DataFlavor.imageFlavor);
                }
            };
        }

        public int getSourceActions(JComponent c) {
            return TransferHandler.COPY;
        }

        public boolean importData(JComponent c, Transferable t) {
            System.err.print("importData: ");
            JLabel label = (JLabel)c;

            if (t.isDataFlavorSupported(DataFlavor.imageFlavor)) {
                System.err.println("imageFlavour");

                try {
                    // Get the data and set our label's image icon to the new image.
                    // Save a copy of the image so we can support dragging it out
                    image = (Image)t.getTransferData(DataFlavor.imageFlavor);
                    label.setIcon(new ImageIcon(image));
                }
                catch (UnsupportedFlavorException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                return true;
            }
            else if (t.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                System.err.println("javaFileListFlavour");

                try {
                    // Get the data and set our label's image icon to the new image.
                    java.util.List<File> files = (java.util.List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);
                    File f = files.get(0);

                    System.err.println("filePath: " + f.getAbsolutePath());
                    ImageIcon iIcon = new ImageIcon(f.getAbsolutePath());
                    image = iIcon.getImage();
                    imageLabel.setIcon(iIcon);
                }
                catch (UnsupportedFlavorException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }

            System.err.println("rejecting");
            return false;
        }

        public boolean canImport(JComponent c, DataFlavor[] transferFlavors) {
            System.err.print("canImport: ");
            for(int i=0; i<transferFlavors.length; i++) {
                DataFlavor df = transferFlavors[i];
                if (df.equals(DataFlavor.imageFlavor) || df.equals(DataFlavor.javaFileListFlavor)) {
                    System.err.println("true");
                    return true;
                }
            }
            System.err.println("false");
            return false;
        }

        protected void exportDone(JComponent c, Transferable data, int action) {
            System.out.println("exportDone");
        }

    }

    // A simple recogizer for the drag gesture
    // The mouseDragged method is called whenever the mouse button is down and
    // the mouse is moving. We only want to initiate drag & drop, for each drag
    // gesture. As such, we only take action the first time mouseDragged is called,
    // resetting whenever the mouse button is released.
    private class DragGesture extends MouseInputAdapter {
        private boolean armed = true;

        public void mouseDragged(MouseEvent e) {

            // Enter the conditional only once, at the start of the drag
            if (armed) {
                System.err.println("Drag starting");

                // Initiate drag and drop
                JComponent c = (JComponent)e.getSource();
                TransferHandler handler = c.getTransferHandler();
                handler.exportAsDrag(c, e, TransferHandler.COPY);

                armed = false;
            }
        }

        public void mouseReleased(MouseEvent e) {
            // Get ready for the next drag
            armed = true;
        }
    }

   ///////////////

    public static void main(String[] args) {
        new DragNDropDemo();
    }
}
