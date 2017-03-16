
/**
 * From "Core Java, Vol 2"
 * 
 * @version 1.20 1999-04-26
 * @author Cay Horstmann
 * 
 * Modified by Byron Weber Becker, 2012-03-22, to better indicate starting
 * multiple threads and to explicitly show the exceptions thrown by the
 * bad worker thread.
 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Demo4 {
	public static void main(String[] args) {
		JFrame frame = new SwingThreadFrame();
		frame.setVisible(true);
	}
}

class SwingThreadFrame extends JFrame {

	private DefaultListModel model = new DefaultListModel();
	DefaultListModel exceptions = new DefaultListModel();
	private int numGoodWorkerThreads = 0;
	private int numBadWorkerThreads = 0;
	private JButton startGoodThread = new JButton("Start Good Thread");
	private JLabel numGoodThreads = new JLabel("" + this.numGoodWorkerThreads);
	private JButton startBadThread = new JButton("Start Bad Thread");
	private JLabel numBadThreads = new JLabel("" + this.numBadWorkerThreads);

	public SwingThreadFrame() {
		this.setTitle("SwingThread");
		this.setSize(400, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ExceptionHandler.registerExceptionHandler(this.exceptions);

		Box vert = Box.createVerticalBox();
		Box good = Box.createHorizontalBox();
		Box bad = Box.createHorizontalBox();

		good.add(startGoodThread);
		good.add(Box.createHorizontalStrut(20));
		good.add(numGoodThreads);

		bad.add(startBadThread);
		bad.add(Box.createHorizontalStrut(20));
		bad.add(numBadThreads);

		JList list = new JList(this.model);
		// list.setPreferredSize(new Dimension(300, 300));
		// list.setMinimumSize(new Dimension(300, 300));
		JScrollPane scrollPane = new JScrollPane(list);

		JList excList = new JList(this.exceptions);
		JScrollPane excSP = new JScrollPane(excList);

		vert.add(good);
		vert.add(bad);
		vert.add(new JLabel("Model"));
		vert.add(scrollPane);
		vert.add(new JLabel("Exceptions"));
		vert.add(excSP);
		this.getContentPane().add(vert, BorderLayout.CENTER);

		startGoodThread.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				new GoodWorkerThread(model).start();
				numGoodWorkerThreads += 1;
				numGoodThreads.setText("" + numGoodWorkerThreads);
			}
		});

		startBadThread.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				new BadWorkerThread(model).start();
				numBadWorkerThreads += 1;
				numBadThreads.setText("" + numBadWorkerThreads);
			}
		});

	}
}

class BadWorkerThread extends Thread {

	private DefaultListModel model;
	private Random generator;

	public BadWorkerThread(DefaultListModel aModel) {
		model = aModel;
		generator = new Random();
	}

	public void run() {
		while (true) {
			Integer i = new Integer(generator.nextInt(10));

			if (model.contains(i))
				model.removeElement(i);
			else
				model.addElement(i);

			//yield();
			try { sleep(5); } catch (InterruptedException e ) {}
		}
	}
}

class GoodWorkerThread extends Thread {
	private DefaultListModel model;
	private Random generator;

	public GoodWorkerThread(DefaultListModel aModel) {
		model = aModel;
		generator = new Random();
	}

	public void run() {
		while (true) {
			final Integer i = new Integer(generator.nextInt(10));
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					// Model updates the UI...
					if (model.contains(i))
						model.removeElement(i);
					else
						model.addElement(i);
				}
			});
			//yield();
			try { sleep(5); } catch (InterruptedException e ) {}
		}
	}
}

/**
 * This class catches the AWT exceptions and makes sure they're actually
 * displayed in the application.
 */
class ExceptionHandler implements Thread.UncaughtExceptionHandler {
	private DefaultListModel exceptions;
	
	private ExceptionHandler(DefaultListModel exceptions) {
		this.exceptions = exceptions;
	}
	
	public void uncaughtException(Thread t, Throwable e) {
		handle(e);
	}

	public void handle(Throwable throwable) {
		try {
			final Throwable tCopy = new Throwable(throwable);
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					exceptions.addElement(new Throwable(tCopy));
				}
			});
		} catch (Throwable t) {
			// don't let the exception get thrown out, will cause infinite
			// looping!
		}
	}

	public static void registerExceptionHandler(DefaultListModel exceptions) {
		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(exceptions));
		System.setProperty("sun.awt.exception.handler",
				ExceptionHandler.class.getName());
	}
}