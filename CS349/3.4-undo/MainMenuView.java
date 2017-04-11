// CS 349 Undo Demo
// Daniel Vogel (2013)

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;


// a main menu view
public class MainMenuView extends JMenuBar  implements Observer {
	

	// Undo menu items
	private JMenuItem undoMenuItem;
	private JMenuItem redoMenuItem;
	
	// the model that this view is showing
	private Model model;
	
	public MainMenuView(Model model_) {
		
		// set the model
		model = model_;
		model.addObserver(this);

		
		// create a menu UI with undo/redo
		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Edit");
		this.add(fileMenu);
		this.add(editMenu);
		
		// Create a "quit" menu item and add it to the file menu
		JMenuItem quitMenuItem = new JMenuItem("Quit");
		fileMenu.add(quitMenuItem);

		// quit menu controller
		quitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});

		// create undo and redo menu items
		undoMenuItem = new JMenuItem("Undo");
		undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
				ActionEvent.CTRL_MASK));
		redoMenuItem = new JMenuItem("Redo");
		redoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,
				ActionEvent.CTRL_MASK));
		editMenu.add(undoMenuItem);
		editMenu.add(redoMenuItem);
		
		// controllers for undo menu item
		undoMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				model.undo();
			}
		});
		// controller for redo menu item
		redoMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				model.redo();
			}
		});
	}

	@Override
	public void update(Observable arg0, Object arg1) {
			undoMenuItem.setEnabled(model.canUndo());
			redoMenuItem.setEnabled(model.canRedo());	
	}

}
