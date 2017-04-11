
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class PicturePanel extends JPanel implements ClipboardOwner {

	// Show 12 pictures
	private DTPicture[] pics = new DTPicture[12];

	// Data to load images into some of the 12 pictures.
	private String[] picNames = new String[] { "Maya", "Anya", "Laine",
			"Cosmo", "Adele", "Alexi" };

	// The currently selected picture's index, or -1 if none is currently
	// selected.
	private int selectedPic = -1;
	
	// Buttons to make Cut/Copy/Paste visible to viewers
	private JButton cutButton = new JButton("Cut");
	private JButton copyButton = new JButton("Copy");
	private JButton pasteButton = new JButton("Paste");


	public PicturePanel() {
		super(new BorderLayout());
		this.layoutComponent();
		this.registerControllers();

	}
	
	private void layoutComponent() {
		JPanel mugshots = new JPanel(new GridLayout(3, 4));

		for (int i = 0; i < this.pics.length; i++) {
			if (i < this.picNames.length) {
				this.pics[i] = DTPicture.getPictureFromFile("images/"
						+ picNames[i] + ".jpg");
			} else {
				this.pics[i] = DTPicture.getEmptyPicture();
			}
			mugshots.add(this.pics[i]);
		}
		this.add(mugshots, BorderLayout.CENTER);

		this.setPreferredSize(new Dimension(630, 520));
		this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// Set up cut/copy/paste buttons to make the actions clearer during
		// demos
		JPanel buttons = new JPanel(new FlowLayout());

		buttons.add(cutButton);
		buttons.add(copyButton);
		buttons.add(pasteButton);

		this.add(buttons, BorderLayout.SOUTH);
		
	}
	
	private void registerControllers() {
		// The transfer handler handles the drag n' drop and cut 'n paste
		// infrastructure
		PictureTransferHandler picHandler = new PictureTransferHandler();
		// This maintains the index of the currently selected picture
		// (this.selectedPic)
		FocusListener fl = new PicFocusListener();
		
		for (int i = 0; i < this.pics.length; i++) {
			this.pics[i].setTransferHandler(picHandler);
			this.pics[i].addFocusListener(fl);
		}
		
		cutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doCut();
			}
		});
		
		copyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doCopy();
			}
		});
		
		pasteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doPaste();
			}
		});

	}
	
	// Called when this component no longer owns the clipboard
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
		System.err.println("Lost clipboard ownership for " + selectedPic);
	}
	

	/*
	 * Copy the currently selected picture.
	 */
	private void doCopy() {

		// Get the system clipboard
		Clipboard systemClipboard = Toolkit.getDefaultToolkit()
				.getSystemClipboard();

		// Create a transferable object encapsulating all the info for the copy
		Transferable transferObject = new PictureTransferable(pics[selectedPic]);

		// Now set the contents of the clipboard to our transferable object
		systemClipboard.setContents(transferObject, this);
		
	}

	private void doCut() {
		this.doCopy(); // most of a cut is the same as a copy
		this.pics[selectedPic].setImage(null);
	}

	private void doPaste() {

		// Grab system clipboard
		Clipboard systemClipboard = Toolkit.getDefaultToolkit()
				.getSystemClipboard();

		// For our own edification, print out the data formats available on the
		// clipboard
		for (DataFlavor flavor : systemClipboard.getAvailableDataFlavors()) {
			System.err.println("Flavor: " + flavor);
		}

		// Check if we can get the data as an image
		if (systemClipboard.isDataFlavorAvailable(DataFlavor.imageFlavor)) {
			try {
				// Grab the data, set the selected picture to the provided image
				Image img = (Image) systemClipboard
						.getData(DataFlavor.imageFlavor);
				assert img != null;
				this.pics[selectedPic].setImage(img);
			} catch (UnsupportedFlavorException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * A listener to help us keep track of which picture has been selected.
	 */
	class PicFocusListener implements FocusListener {
		public void focusGained(FocusEvent e) {
			Component c = e.getComponent();
			for (int i = 0; i < pics.length; i++) {
				if (pics[i] == c) {
					selectedPic = i;
					return;
				}
			}
			assert false;
		}

		public void focusLost(FocusEvent e) {
		}
	}


	/*
	 * Create a set of cut/copy/paste menus. They call doCopyOrCut and doPaste.
	 */
	public JMenuBar createMenuBar() {
		JMenuBar menubar = new JMenuBar();

		JMenu file = new JMenu("File");
		JMenuItem quit = new JMenuItem("Quit");
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				ActionEvent.META_MASK));
		file.add(quit);

		JMenu edit = new JMenu("Edit");
		JMenuItem cut = new JMenuItem("Cut");
		cut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doCut();
			}
		});
		cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				ActionEvent.META_MASK));

		JMenuItem copy = new JMenuItem("Copy");
		copy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doCopy();
			}
		});
		copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				ActionEvent.META_MASK));

		JMenuItem paste = new JMenuItem("Paste");
		paste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doPaste();
			}
		});
		paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
				ActionEvent.META_MASK));

		edit.add(cut);
		edit.add(copy);
		edit.add(paste);

		menubar.add(file);
		menubar.add(edit);
		return menubar;
	}

}
