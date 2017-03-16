/*
 * An application to calculate and display prime numbers
 * up to 250,000.  To demonstrate the wrong way to do it.
 * The task takes so long that it freezes the user interface.
 *
 * @author Byron Weber Becker
 * @date 19-June-2009
 * @date 22-Mar-2012  factored out common code
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Demo1 {
	public static void main(String[] args) {

		Model1 model = new Model1(1, 250000);
		View1 view = new View1(model);

		JFrame frame = new JFrame("Demo1: UI Thread Blocks");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(view);
		frame.pack();
		frame.setVisible(true);
	}
}


@SuppressWarnings("serial")
class View1 extends AbstractView<Model1> {

	public View1(Model1 model) {
		super(model);
	}

	public void update() {
		primesList.setListData(model.getPrimes());
	}

	protected void registerControllers() {
		// Handle presses of the start button
		this.startStopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startStopButton.setText("Stop");
				model.calculatePrimes();
			}
		});
	}
}

/**
 * Find all the prime numbers between min and max in the event handling thread.
 * No attempt to break up the job.
 */
class Model1 extends AbstractModel {

	public Model1(int min, int max) {
		super(min, max);
	}

	public void calculatePrimes() {
		for (int i = this.min; i < this.max; i++) {
			if (this.isPrime(i)) {
				this.addPrime(i);
			}
		}
		updateAllViews();
	}
}
