
import java.util.Vector;

/** AbstractPrimesModel contains the infrastructure for calculating 
 * a set of primes, including the code to support MVC.  Subclasses illustrate
 * the right and wrong ways to support a long task in an interactive
 * application.
 * 
 * @author bwbecker
 * @date 22-Mar-2012  factored out common code
 */
public abstract class AbstractModel {
	protected int min;
	protected int max;
	
	protected Vector<Integer> primes = new Vector<Integer>();
	
	protected Vector<IView> views = new Vector<IView>();
	
	/** Instantiate a class to calculate all the prime numbers between min and max. */
	protected AbstractModel(int min, int max) {
		this.min = min;
		this.max = max;
	}
	
	/** Determine whether n is prime.  For the purposes of a long-running task
	 * demo, we DO NOT want to optimize this! */
	protected final boolean isPrime(int n) {
		for (int i = 2; i < n; i++) {
			if (n % i == 0)
				return false;
		}
		return true;
	}
	
	public abstract void calculatePrimes();
		
	protected synchronized void addPrime(int i) {
		this.primes.add(new Integer(i));
	}

	/** Get the list of prime numbers (returns a copy). */
	public synchronized Vector<Integer> getPrimes() {
		return new Vector<Integer>(this.primes);
	}
	
	/*
	 * Stuff to support the observers.
	 */
	
	/** Add a view/observer. */
	public void addView(IView view) {
		this.views.add(view);
		view.update();
	}

	/** Remove a view/observer. */
	public void removeView(IView view) {
		this.views.remove(view);
	}

	/** Update all views/observers with details about a change to the model. */
	protected void updateAllViews() {
		for (IView view : this.views) {
			view.update();
		}
	}

}

