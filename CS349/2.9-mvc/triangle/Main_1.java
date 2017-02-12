import javax.swing.JFrame;

import model.TriangleModel;

public class Main_1 {

	public static void main(String[] args) {
		TriangleModel model = new TriangleModel();
		view.SimpleTextView view = new view.SimpleTextView(model);

		JFrame frame = new JFrame("Triangle");
		frame.getContentPane().add(view);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
