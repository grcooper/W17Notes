
/**
 * Classes implement this interface to observe another object;  that
 * is, the update method will be called when something of significance 
 * happens in the observed object.
 */
public interface IView {
	/**
	 * Something of significance happened in the observed object.
	 */
	public void update();

}

