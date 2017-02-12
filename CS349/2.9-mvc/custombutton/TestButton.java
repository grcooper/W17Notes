import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TestButton extends JFrame {

	int count =0;
	int count2 =0;
	JLabel jl = null;
	JLabel jl2 = null;

	public TestButton(){
		this.getContentPane().setLayout(new GridLayout(2,1));
		JPanel jp1 = new JPanel();
		jp1.setLayout(new FlowLayout());

		OnPressButton opb = new OnPressButton("Press Me");
		jl = new JLabel("Count = "+count);
		opb.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				count++;
				jl.setText("Count = "+count);
			}
		});
		jp1.add(opb);
		jp1.add(jl);
		this.add(jp1);

		JPanel jp2 = new JPanel();
		jp2.setLayout(new FlowLayout());

		JButton jb = new JButton("Default");
		jl2 = new JLabel("Default = "+count2);
		jb.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				count2++;
				jl2.setText("Default = "+count2);
			}
		});
		jp2.add(jb);
		jp2.add(jl2);
		this.add(jp2);
	}
	public static void main(String args[]){
		TestButton tb = new TestButton();
		tb.setSize(800, 200);
		tb.setVisible(true);
	}
}
