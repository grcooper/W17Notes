
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;


/*
 * A DTPicture can display an image and take care
 * of data transfer (hence the DT in the name) operations.
 */
class DTPicture extends JComponent {
	private Image image;

	/* Create a picture using either getPictureFromFile or getEmptyPicture */
    private DTPicture(Image image) {
        super();
		this.image = image;
		this.setFocusable(true);
		
		DragGesture dg = new DragGesture();
		this.addMouseListener(dg);
        this.addMouseMotionListener(dg);
		this.addFocusListener(new HighlightWhenFocusedListener());
    }
    
    /* Get an empty picture (displays nothing) */
    public static DTPicture getEmptyPicture() {
    	return new DTPicture(null);
    }
    
    /* Get a picture from an image file. */
    public static DTPicture getPictureFromFile(String path) {
    	if (path == null) {
    		return new DTPicture(null);
    	}
    	
        java.net.URL imageURL = TransferDemo.class.getResource(path);

        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return new DTPicture(null);
        } else {
            return new DTPicture(new ImageIcon(imageURL, path).getImage());
        }
    }

    public void setImage(Image image) {
        this.image = image;
        this.repaint();
    }

    public Image getImage() {
        return this.image;
    }
    

	protected void paintComponent(Graphics graphics) {
		Graphics g = graphics.create();

		// Draw in our entire space, even if isOpaque is false.
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, image == null ? 125 : image.getWidth(this),
				image == null ? 125 : image.getHeight(this));

		if (image != null) {
			// Draw image at its natural size of 125x125.
			g.drawImage(image, 0, 0, this);
		}

		// Add a border, red if picture currently has focus
		if (this.isFocusOwner()) {
			g.setColor(Color.RED);
		} else {
			g.setColor(Color.BLACK);
		}
		g.drawRect(0, 0, image == null ? 125 : image.getWidth(this),
				image == null ? 125 : image.getHeight(this));
		g.dispose();
	}
	
	class HighlightWhenFocusedListener implements FocusListener {
		public void focusGained(FocusEvent e) {
			// Draw the component with a red border
			// indicating that it has focus.
			DTPicture.this.repaint();
		}

		public void focusLost(FocusEvent e) {
			// Draw the component with a black border
			// indicating that it doesn't have focus.
			DTPicture.this.repaint();
		}

	}
	
	/*---------------------- Drag 'n Drop support ----------------------*/
	
	// MouseInputAdapter implements and provides default methods for 
	// both MouseListener and MouseMotionListener interfaces.
	class DragGesture extends MouseInputAdapter {
	    private MouseEvent firstMouseEvent = null;

	    public void mouseClicked(MouseEvent e) {
			// Since the user clicked on us, let's get focus!
			requestFocusInWindow();
		}	
		
	    public void mousePressed(MouseEvent e) {
	        //Don't bother to drag if there is no image.
	        if (DTPicture.this.image == null) return;

	        firstMouseEvent = e;
	        // prevent any other listeners from acting on this event
	        e.consume();
	    }
	    
	    public void mouseReleased(MouseEvent e) {
	        firstMouseEvent = null;
	    }

	    public void mouseDragged(MouseEvent e) {
            //Don't bother to drag if the component displays no image.
            if (DTPicture.this.image == null) return;

            if (firstMouseEvent != null) {
            	// prevent other listeners from acting on this event
                e.consume();

                int dx = Math.abs(e.getX() - firstMouseEvent.getX());
                int dy = Math.abs(e.getY() - firstMouseEvent.getY());
                //Arbitrarily define a 5-pixel shift as the
                //official beginning of a drag.
                if (dx > 5 || dy > 5) {
                    //This is a drag, not a click.

                    //If they are holding down the control key, COPY rather than MOVE
                    int ctrlMask = InputEvent.CTRL_DOWN_MASK;
                    int action = ((e.getModifiersEx() & ctrlMask) == ctrlMask) ?
                          TransferHandler.COPY : TransferHandler.MOVE;

                    JComponent c = (JComponent)e.getSource();
                    TransferHandler handler = c.getTransferHandler();
                    
                    //Tell the transfer handler to initiate the drag.
                    handler.exportAsDrag(c, firstMouseEvent, action);
                    firstMouseEvent = null;
                }
            }
        }	
	}

}
