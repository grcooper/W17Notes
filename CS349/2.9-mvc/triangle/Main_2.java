import javax.swing.JFrame;

import model.TriangleModel;

public class Main_2 {

	public static void main(String[] args) {
		model.TriangleModel model = new TriangleModel();
		view.TextView view = new view.TextView(model);

		JFrame frame = new JFrame("Triangle");
		frame.getContentPane().add(view);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
