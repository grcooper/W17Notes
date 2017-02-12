// HelloMVC: a simple MVC example
// the model is just a counter
// inspired by code by Joseph Mack, http://www.austintek.com/mvc/

// View interface
interface IView {
	public void updateView();
}

public class Model {
	// the data in the model, just a counter
	private int counter;
	// the view
	IView view;

	// set the view observer
	public void setView(IView view) {
		this.view = view;
		// update the view to current state of the model
		view.updateView();
	}

	public int getCounterValue() {
		return counter;
	}

	public void incrementCounter() {
		if (counter < 5) {
			counter++;
			System.out.println("Model: increment counter to " + counter);
			notifyObserver();
		}
	}

	// notify the IView observer
	private void notifyObserver() {
		System.out.println("Model: notify View");
		view.updateView();
	}
}
