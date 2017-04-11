// CS 349 Undo Demo
// Daniel Vogel (2013)

import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.undo.UndoManager;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;

public class Main {
	
	Model model;

	public static void main(String[] args) {
		new Main();
	}

	public Main() {

		JFrame frame = new JFrame("UndoDemo");

		// create Model and initialize it
		// (value, min, max in this model)
		model = new Model(22, 0, 100);

		// create View
		View view = new View(model);
		
		// create Menu View
		MainMenuView menuView = new MainMenuView(model);

		// add views to the window
		frame.getContentPane().add(view);
		frame.setJMenuBar(menuView);

		frame.setPreferredSize(new Dimension(300, 220));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// let all the views know that they're connected to the model
		model.updateViews();
	}
}
