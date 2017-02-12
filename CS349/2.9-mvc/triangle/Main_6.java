import javax.swing.*;

import model.TriangleModel;
import view.*;
import java.awt.BorderLayout;

public class Main_6 {

	public static void main(String[] args) {
		model.TriangleModel model = new TriangleModel();
		GraphicalView vGraphical = new GraphicalView(model);
		TextView vText = new TextView(model);

		JFrame frame = new JFrame("Triangle");
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(vText, BorderLayout.NORTH);
		frame.getContentPane().add(vGraphical, BorderLayout.CENTER);

		frame.setSize(480, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
