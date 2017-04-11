// CS 349 Undo Demo
// Daniel Vogel (2013)
// Added incValue to have an example that doesn't look like momento  BWBecker (2016)

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.Observable;

import javax.swing.undo.*;

// A simple model that is undoable
public class Model extends Observable {

	// Undo manager
	private UndoManager undoManager;

	private int value;
	private int min;
	private int max;

	Model(int value, int min, int max) {
		undoManager = new UndoManager();
		this.value = value;
		this.min = min;
		this.max = max;
	}

	public void updateViews() {
		setChanged();
		notifyObservers();
	}

	public int getValue() {
		return value;
	}

	public void setValue(int v) {
		System.out.println("Model: set value to " + v);


		// create undoable edit
		UndoableEdit undoableEdit = new AbstractUndoableEdit() {
			// capture variables for closure
			final int oldValue = value;
			final int newValue = v;

			// Method that is called when we must redo the undone action
			public void redo() throws CannotRedoException {
				super.redo();
				value = newValue;
				System.out.println("Model: redo value to " + value);
				setChanged();
				notifyObservers();
			}

			public void undo() throws CannotUndoException {
				super.undo();
				value = oldValue;
				System.out.println("Model: undo value to " + value);
				setChanged();
				notifyObservers();
			}
		};

		// Add this undoable edit to the undo manager
		undoManager.addEdit(undoableEdit);

		value = v;
		setChanged();
		notifyObservers();
	}


	public void incValue() {
		System.out.println("Model: increment value ");


		// create undoable edit
		UndoableEdit undoableEdit = new AbstractUndoableEdit() {

			// Method that is called when we must redo the undone action
			public void redo() throws CannotRedoException {
				super.redo();
				value = value + 1;
				System.out.println("Model: redo value to " + value);
				setChanged();
				notifyObservers();
			}

			public void undo() throws CannotUndoException {
				super.undo();
				value = value - 1;
				System.out.println("Model: undo value to " + value);
				setChanged();
				notifyObservers();
			}
		};

		// Add this undoable edit to the undo manager
		undoManager.addEdit(undoableEdit);

		value = value + 1;
		setChanged();
		notifyObservers();
	}



	public void decValue() {
		System.out.println("Model: decrement value ");


		// create undoable edit
		UndoableEdit undoableEdit = new AbstractUndoableEdit() {

			// Method that is called when we must redo the undone action
			public void redo() throws CannotRedoException {
				super.redo();
				value = value - 1;
				System.out.println("Model: redo value to " + value);
				setChanged();
				notifyObservers();
			}

			public void undo() throws CannotUndoException {
				super.undo();
				value = value + 1;
				System.out.println("Model: undo value to " + value);
				setChanged();
				notifyObservers();
			}
		};

		// Add this undoable edit to the undo manager
		undoManager.addEdit(undoableEdit);

		value = value - 1;
		setChanged();
		notifyObservers();
	}


	public int getMin() {
		return min;
	}

	public void setMin(int v) {
		System.out.println("Model: set min to " + v);

		// capture variables for closure
		final int oldValue = min;
		final int newValue = v;

		// create undoable edit
		UndoableEdit undoableEdit = new AbstractUndoableEdit() {

			// Method that is called when we must redo the undone action
			public void redo() throws CannotRedoException {
				super.redo();
				min = newValue;
				setChanged();
				notifyObservers();
			}

			public void undo() throws CannotUndoException {
				super.undo();
				min = oldValue;
				setChanged();
				notifyObservers();
			}
		};

		// Add this undoable edit to the undo manager
		undoManager.addEdit(undoableEdit);

		// we can finally set it here ...
		min = v;
		setChanged();
		notifyObservers();
	}

	public int getMax() {
		return max;
	}

	public void setMax(int v) {
		System.out.println("Model: set max to " + v);

		// capture variables for closure
		final int oldValue = max;
		final int newValue = v;

		// create undoable edit
		UndoableEdit undoableEdit = new AbstractUndoableEdit() {

			// Method that is called when we must redo the undone action
			public void redo() throws CannotRedoException {
				super.redo();
				max = newValue;
				setChanged();
				notifyObservers();
			}

			public void undo() throws CannotUndoException {
				super.undo();
				max = oldValue;
				setChanged();
				notifyObservers();
			}
		};

		// Add this undoable edit to the undo manager
		undoManager.addEdit(undoableEdit);

		// we can finally set it here ...
		max = v;
		setChanged();
		notifyObservers();
	}

	// undo and redo methods
	// - - - - - - - - - - - - - -

	public void undo() {
		if (canUndo())
			undoManager.undo();
	}

	public void redo() {
		if (canRedo())
			undoManager.redo();
	}

	public boolean canUndo() {
		return undoManager.canUndo();
	}

	public boolean canRedo() {
		return undoManager.canRedo();
	}

}
