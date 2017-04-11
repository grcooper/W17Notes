// CS 349 Undo Demo
// Daniel Vogel (2013)

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.UndoableEdit;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

class View extends JPanel implements Observer {

	// the view's main user interface
	private JSlider slider;
	private JTextField text;
	private JButton increment;
	private JButton decrement;

	// the model that this view is showing
	private Model model;

	View(Model model_) {

		// set the model
		model = model_;
		model.addObserver(this);

		// create the view UI
		slider = new JSlider();
		text = new JTextField("X");
		increment = new JButton("+");
		decrement = new JButton("-");


		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		Box buttons = Box.createHorizontalBox();
		buttons.add(increment);
		buttons.add(decrement);
		this.add(text);
		this.add(slider);
		this.add(buttons);

		// this is the wrong place to save undo edits
		// change the flag and see what happens
		final boolean undoEverything = false;
		if (undoEverything) {
			slider.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent arg0) {
					model.setValue(slider.getValue());
				}
			});
		} else {

			// controller for when a drag event finishes
			// this is the right undo "chunk"
			slider.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					model.setValue(slider.getValue());
				}
			});
		}
		
		// add a controller for text edits too
		// (will only fire when enter is pressed after editing ...)
		text.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					model.setValue(Integer.parseInt(text.getText()));				
				} catch (NumberFormatException ex) {
					// not a number, just update views to reset it
					// (need to be careful here not to insert an undo)
					model.updateViews();
				}
			}
		});

		increment.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.incValue();
			}
		});

		decrement.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.decValue();
			}
		});
	}

	
	// Observer interface
	@Override
	public void update(Observable arg0, Object arg1) {
		System.out.println("View: update");
		slider.setValue(model.getValue());
		slider.setMinimum(model.getMin());
		slider.setMaximum(model.getMax());
		text.setText(Integer.toString(model.getValue()));
	}
}
