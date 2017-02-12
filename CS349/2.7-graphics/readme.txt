CS 349 Java Example Code
=========================================

To make an example, use the included makefile with 
the name of the main java file passed as a variable. For example, to make SimpleDraw.java:

	make NAME=SimpleDraw

Then, to run:

	java ./SimpleDraw

Or you can even do it one step:

	make run NAME=SimpleDraw


Examples
--------

SimpleDraw        Draws red and blue X in window.

MouseEventDemo    Mouse listeners and mouse motion listeners.

RotateLine	      Use transformations to rotate line

RotateTriangle1   Animates a triangle rotating around a circle using transformations.

ClosestPointDemo  Uses two methods to compute distance from 
                  mouse to closest point on line.

ShapeDemo2        Demo of MyShape class: draw shapes using mouse.

TransConcatDemo   Demo of different concatenation orders of matrix transforms using
				  MyShape class. Click the window to change the order.


				  