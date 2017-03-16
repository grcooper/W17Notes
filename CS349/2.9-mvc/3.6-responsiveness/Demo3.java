
/**
 * An application to calculate and display prime numbers
 * up to 250,000.  The task takes a long time, so we put
 * it in a thread that periodically checks if has been
 * canceled.  Updates to the UI are done with invokeLater.
 *
 * @author Byron Weber Becker
 * @date 19-June-2009
 * @date 22-Mar-2012  factored out common code
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Demo3 {
	public static void main(String[] args) {
		Model3 model = new Model3(1, 250000);
		View3 view = new View3(model);

		JFrame frame = new JFrame("Demo3: Separate Thread");
		frame.getContentPane().add(view);
		frame.pack();
		frame.setVisible(true);
	}
}

@SuppressWarnings("serial")
class View3 extends AbstractView<Model3> {

	public View3(Model3 model) {
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
			startStopButton.invalidate();
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

class Model3 extends AbstractModel {
	private boolean cancelled = false;
	private boolean running = false;
	private int current;

	public Model3(int min, int max) {
		super(min, max);
		this.current = this.min;
	}

	public void calculatePrimes() {

		// create a new thread and do task there ...
		new Thread() {

			public void run() {
				running = true;
				long start = System.currentTimeMillis();
				while (true) {
					if (cancelled || current > max) {
						running = false;
						updateAllViewsInUiThread();
						return;
					} else if (isPrime(current)) {
						addPrime(current);
					}
					current += 1;
					// update every 100 ms
					if (System.currentTimeMillis() - start >= 100) {
						updateAllViewsInUiThread();
						start = System.currentTimeMillis();
					}
				}
			}
			private void updateAllViewsInUiThread() {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						updateAllViews();
					}
				});
			}
		}.start();
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
