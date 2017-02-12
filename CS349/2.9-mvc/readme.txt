CS 349 Java Example Code - MVC
=========================================

Use the included makefiles with the name of the main java 
file passed as a variable. For example, to make and run
Hello.java:

	make run NAME="Hello"

EXAMPLES
========

hellomvc*/	Simple MVC examples using an incremental counter model. Each directory has
			a make file.

			hellomvc1/		separate view and controller classes, only 1 view
			hellomvc2/		separate view and controller classes, multiple views
			hellomvc3/		controller combined into view, multiple views
			hellomvc4/		using Java's Observer interface and Observable base class

triangle/	More complex MVC examples using a right-angle triangle model. Also shows how
			to  use packages to organize code and a custom LayoutManager. Set makefile 
			NAME to one of the following startup classes to run.

			Main_1			using SimpleTextView, a first try at a view
			Main_2			improved TextView
			Main_3			multiple views
			Main_4			a direct manipulation GraphicalView
			Main_5, Main_6	multiple views using a GraphicalView

jtable/		Using the JTable abstract model widget.

			SimpleTable 	uses JTable with default model created by passing data to 
							constructor.

			CustomTable 	uses AbstractTableModel to customize table access and views

			MoreCustomTable shows how to use TableCellRenderer, TableCellEditor, and also 
							demos JColorChooser colour picker							