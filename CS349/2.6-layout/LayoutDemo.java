//package layoutDemo;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Color;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import javax.swing.BorderFactory;

/* Demonstrate various layout managers available in Java,
 * including defining your own layout manager.
 * 
 * @author Byron Weber Becker
 */
public class LayoutDemo {
	public static void main(String[] args) {
		// These are stacked bottom to top, so put the first one
		// to be discussed last.
		new LayoutFrame("NestedLayout", new DemoNestedLayout());
		new LayoutFrame("RandomLayout", new DemoRandomLayout());
		new LayoutFrame("GridBagLayout", new DemoGridBagLayout());
		new LayoutFrame("SpringLayout", new DemoSpringLayout());
		new LayoutFrame("BorderLayout", new DemoBorderLayout());

		new LayoutFrame("BoxLayout3", new DemoBoxLayout3());
		new LayoutFrame("BoxLayout2", new DemoBoxLayout2());
		new LayoutFrame("BoxLayout1", new DemoBoxLayout1());

		new LayoutFrame("GridLayout", new DemoGridLayout());
		new LayoutFrame("FlowLayout", new DemoFlowLayout());
		new LayoutFrame("NullLayout", new DemoNullLayout());
	}

}

class LayoutFrame extends JFrame {
	private static int xPos = 10;
	private static int yPos = 10;
	private static final int OFFSET = 50;

	public LayoutFrame(String title, DemoPanel contents) {
		super(title);
		this.setContentPane(contents);
		this.setSize(300, 130);
		this.setLocation(xPos, yPos);
		xPos += OFFSET;
		yPos += OFFSET;
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

}

/**
 * Set attributes common to all panels.
 */
class DemoPanel extends JPanel {
	public DemoPanel() {
		super();
		this.setBackground(Color.GRAY);
	}
}

class DemoBorderLayout extends DemoPanel {
	public DemoBorderLayout() {
		super();
		this.setLayout(new BorderLayout());
		this.add(new JButton("North"), BorderLayout.NORTH);
		this.add(new JTextField("South"), BorderLayout.SOUTH);
		this.add(new JButton("East"), BorderLayout.EAST);
		this.add(new JButton("South"), BorderLayout.SOUTH);

		// Layouts can be nested
		// Box is an easy-to-create panel with a BoxLayout
		Box b = Box.createVerticalBox();
		b.add(new JButton("Box this!"));
		b.add(new JButton("Box that!"));
		b.add(Box.createVerticalGlue());
		b.add(new JButton("Box whatever"));
		this.add(b, BorderLayout.WEST);

		this.add(new JTextField("Center"), BorderLayout.CENTER);
	}

}

class DemoBoxLayout1 extends DemoPanel {
	// Default behaviour of BoxLayout
	public DemoBoxLayout1() {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(new JButton("One"));
		this.add(new JButton("Two"));
		this.add(new JTextField("Three"));
		this.add(new JButton("Four"));
	}
}

class DemoBoxLayout2 extends DemoPanel {
	public DemoBoxLayout2() {
		super();
		JButton c1 = new JButton("First");
		JTextArea c2 = new JTextArea(10, 40);
		JButton c3 = new JButton("Third");
		c1.setAlignmentX(0.5f);

		// Text areas have infinite maximum size by default, so BoxLayout will
		// grow it to fill all available space unless we place a limit.
		c2.setMaximumSize(new Dimension(200, 150));

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(c1);
		this.add(c2);
		this.add(c3);
	}
}

class DemoBoxLayout3 extends DemoPanel {
	// Using struts and glue
	public DemoBoxLayout3() {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(new JButton("One"));
		this.add(Box.createVerticalGlue());
		this.add(new JButton("Two"));
		this.add(Box.createVerticalStrut(10));
		this.add(new JButton("Three"));
	}
}

class DemoFlowLayout extends DemoPanel {
	public DemoFlowLayout() {
		super();
		this.setLayout(new FlowLayout());
		this.add(new JButton("  One  "));
		this.add(new JTextField("  Two  ", 7));
		this.add(new JLabel("  Three  "));
		this.add(new JCheckBox("  Four  "));

	}

}

class DemoGridBagLayout extends DemoPanel {
	public DemoGridBagLayout() {
		super();
		this.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.gridwidth = 1;
		gc.gridheight = 3;
		gc.weightx = 0.5;
		gc.weighty = 0.5;
		this.add(new JButton("One"), gc);
		gc.fill = GridBagConstraints.NONE;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		gc.gridx = 1;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.WEST;
		this.add(new JTextField("Two"), gc);
		gc.gridy++;
		gc.anchor = GridBagConstraints.CENTER;
		this.add(new JButton("Three"), gc);
		gc.gridy++;
		gc.anchor = GridBagConstraints.EAST;
		this.add(new JButton("Four"), gc);
	}

}

class DemoGridLayout extends DemoPanel {
	private JButton one = new JButton("One");
	private JButton two = new JButton("Two");
	private JButton three = new JButton("Three");
	private JButton four = new JButton("Four");
	private JButton five = new JButton("Five");
	private JButton six = new JButton("Six");

	public DemoGridLayout() {
		super();
		// Set the layout strategy to a grid with 2 rows and 3 columns.
		GridLayout strategy = new GridLayout(2, 3);
		this.setLayout(strategy);

		// Add the components.
		this.add(this.one);
		this.add(this.two);
		this.add(this.three);
		this.add(this.four);
		this.add(this.five);
		this.add(this.six);
	}
}

class DemoNullLayout extends DemoPanel {
	public DemoNullLayout() {
		super();
		this.setLayout(null);
		JButton b1 = new JButton("Button1");
		JButton b2 = new JButton("Button2");
		b1.setBounds(10, 20, 150, 50);
		b2.setBounds(40, 60, 100, 30);
		this.add(b2);
		this.add(b1);
	}

}

class DemoSpringLayout extends DemoPanel {
	private JButton b1 = new JButton("One");
	private JTextArea a1 = new JTextArea("Two");
	private JButton b2 = new JButton("Three");

	public DemoSpringLayout() {
		super();
		SpringLayout layout = new SpringLayout();

		this.setLayout(layout);
		this.add(this.b1);
		this.add(this.a1);
		this.add(this.b2);

		// Add constraints
		// contrain west side of b1 to be 5 pixels from west side of the panel
		// constrain north side of b1 to be 5 pixels from north side of the
		// panel
		layout.putConstraint(SpringLayout.WEST, b1, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, b1, 5, SpringLayout.NORTH,
				this);

		// constrain b2 to be below b1
		layout.putConstraint(SpringLayout.WEST, b2, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, b2, 10, SpringLayout.SOUTH,
						b1);

		// Let the textarea grow as large as requested
		a1.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

		// constrain a1 to be east of the buttons and spread vertically to fill
		// the entire height of the panel
		layout.putConstraint(SpringLayout.WEST, a1, 5, SpringLayout.EAST, b2);
		layout.putConstraint(SpringLayout.NORTH, a1, 5, SpringLayout.NORTH,
				this);

		// Note the order of this and a1 are switched. Don't know why it
		// matters;
		// but it does.
		layout.putConstraint(SpringLayout.SOUTH, this, 5, SpringLayout.SOUTH,
				a1);
		layout.putConstraint(SpringLayout.EAST, this, 5, SpringLayout.EAST, a1);

	}

}


class DemoRandomLayout extends DemoPanel {
	private JButton one = new JButton("One");
	private JButton two = new JButton("Two");
	private JButton three = new JButton("Three");
	private JButton four = new JButton("Four");
	private JButton five = new JButton("Five");
	private JButton six = new JButton("Six");

	public DemoRandomLayout() {
		super();
		this.setLayout(new RandomLayout());

		// Add the components.
		this.add(this.one);
		this.add(this.two);
		this.add(this.three);
		this.add(this.four);
		this.add(this.five);
		this.add(this.six);
	}
}

/**
 * Demo a nested layout -- a container widget that holds other widgets, including
 * other containers that themselves hold widgets and/or other containers, etc.
 *
 * @author bwbecker
 *
 */
class DemoNestedLayout extends DemoPanel {
	public DemoNestedLayout() {
		super();
		DemoPanel north = new DemoGridLayout();
		DemoPanel east = new DemoGridLayout();
		DemoPanel west = new DemoBoxLayout3();

		north.setBorder(BorderFactory.createTitledBorder("north"));
		east.setBorder(BorderFactory.createTitledBorder("east"));
		west.setBorder(BorderFactory.createTitledBorder("west"));

		this.setLayout(new BorderLayout());
		this.add(north, BorderLayout.NORTH);
		this.add(east, BorderLayout.EAST);
		this.add(west, BorderLayout.WEST);

	}
}


class RandomLayout implements LayoutManager {
    public void addLayoutComponent(String name, Component comp) {
        ; // no-op
    }
    public void layoutContainer(Container parent) {
        Component[] components = parent.getComponents();
        Dimension parentSize = parent.getSize();

        for (int i = 0; i < components.length; i++) {
            Component c = components[i];
            c.setSize(c.getPreferredSize());
            int x = (int)(Math.random() * (parentSize.width - c.getWidth()));
            int y = (int)(Math.random() * (parentSize.height - c.getHeight()));
            c.setLocation(x, y);
        }
    }
    public Dimension minimumLayoutSize(Container parent) {
        return new Dimension(400, 300);
    }
    public Dimension preferredLayoutSize(Container parent) {
        return this.minimumLayoutSize(parent);
    }
    public void removeLayoutComponent(Component comp) {
        ; // no-op
    }
}
