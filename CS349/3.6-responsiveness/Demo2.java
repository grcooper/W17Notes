
/*
 * An application to calculate and display prime numbers
 * up to 250,000.  The task takes a long time, so we just 
 * do a little bit of the calculation at a time.  If the
 * calculation isn't done yet, invokeLater is used to 
 * let it run a little longer.  All this processing
 * happens in the Event dispatch thread, but a little
 * at a time so the UI doesn't block.
 * 
 * There is a hard-coded constant in FindPrimesFP that 
 * determines how long it can compute before quiting to 
 * give the UI a chance to update.  It should probably be
 * about 100 rather than 500.
 * 
 * @author Byron Weber Becker
 * @date 19-June-2009
 * @date 22-Mar-2012  factored out common code
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Demo2 {
	public static void main(String[] args) {
		Model2 model = new Model2(1, 250000);
		View2 view = new View2(model);

		JFrame frame = new JFrame("Demo2: UI Thread Works");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(view);
		frame.pack();
		frame.setVisible(true);
	}
}

@SuppressWarnings("serial")
class View2 extends AbstractView<Model2> {

	public View2(Model2 model) {
		super(model);
	}

	public void update() {
		progressBar.setValue(model.progress());
		primesList.setListData(model.getPrimes());
		
		if (model.isDone()) {
			startStopButton.setText("Done");
			startStopButton.setEnabled(false);
		} else if (model.wasCancelled()) {
			startStopButton.setText("Cancelled");
			startStopButton.setEnabled(false);
		} else if (model.isRunning()) {
			startStopButton.setText("Stop");
		}
	}

	protected void registerControllers() {
		this.startStopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (model.isRunning()) {
					model.cancel();
				} else {
					model.calculatePrimes();
				}
			}
		});
	}
}

class Model2 extends AbstractModel {
	private boolean cancelled = false;
	private boolean running = false;
	private int current;		// progress so far

	public Model2(int min, int max) {
		super(min, max);
		this.current = this.min;
	}

	/*
	 * Calculate a some primes in the event thread. If necessary, schedule
	 * ourselves to calculate some more a little bit later.
	 */
	public void calculatePrimes() {
		this.running = true;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				calculateSomePrimes(100);
				if (!cancelled && current <= max) {
					calculatePrimes();
				}
			}
		});
	}

	/**
	 * Calculate some prime numbers for duration ms. 
	 * Quit when we run out of time or we're
	 * cancelled or we've reached the maximum prime to look for.
	 */
	private void calculateSomePrimes(long duration) {
		long start = System.currentTimeMillis();
		while (true) {
			if (this.current > this.max) {
				this.running = false;
				updateAllViews();
				return;
			} else if (System.currentTimeMillis() - start >= duration) {
				updateAllViews();
				return;
			} else if (isPrime(this.current)) {
				this.addPrime(current);
			}
			current += 1;
		}
	}

	public boolean isRunning() {
		return this.running;
	}

	public boolean isDone() {
		return this.current > this.max;
	}
	
	public boolean wasCancelled() {
		return this.cancelled;
	}

	public void cancel() {
		this.cancelled = true;
	}

	public int progress() {
		return (this.current * 100) / (this.max - this.min);
	}

}
