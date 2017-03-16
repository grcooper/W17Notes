CS 349 Java Example Code - Multithreading and long tasks
=========================================

Use the included makefiles with the name of the main java 
file passed as a variable. For example, to make and run
Hello.java:

	make run NAME="Demo1"

EXAMPLES
========

The following examples use the same base classes for their view and model.


Demo1.java		Wrong way to handle long tasks: task blocks UI thread.

Demo2.java		Better way: task is broken up into smaller subtasks and 
				still run in the UI thread.
				
Demo3.java		Best way: task runs in a different thread, and communicates
				with the UI thread in a safe way