
/**
 * An abstract model class for views of the prime numbers model.  It provides a
 * start/stop button, a progress meter, and a text area in which the primes
 * are viewed.  Subclasses must override update() to update the interface
 * appropriately, and registerControllers() to register a controller with the
 * start/stop button.
 * 
 * @author Byron Weber Becker
 * @date 22-Mar-2012  factored out common code
 */

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.*;
import java.awt.Font;


@SuppressWarnings("serial")
abstract public class AbstractView<M extends AbstractModel> extends JPanel implements IView {
	protected M model;
	// our UI widgets
	protected JButton startStopButton = new JButton("Start");
	protected JProgressBar progressBar = new JProgressBar(0, 100);
	protected JList primesList = new JList();

	public AbstractView(M m) {
		this.model = m;

		this.primesList.setFont(new Font(Font.SANS_SERIF, 3, 20));
		this.layoutView();
		this.registerControllers();
		
		this.model.addView(this);
	}
	
	private void layoutView() {
		this.setLayout(new BorderLayout());
		JPanel north = new JPanel(new FlowLayout());
		north.add(this.startStopButton);
		north.add(this.progressBar);
		this.add(north, BorderLayout.NORTH);
		this.add(new JScrollPane(primesList), BorderLayout.CENTER);
	}
	
	abstract protected void registerControllers();

	abstract public void update();
}
