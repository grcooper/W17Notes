# super simple makefile
# call it using 'make NAME=name_of_code_file_without_extension'
# (assumes a .java extension)
NAME = "LayoutDemo"

all:
	@echo "Compiling..."
	javac $(NAME).java

run: all
	@echo "Running..."
	java $(NAME)

swingset:
	java -cp SwingSet2.jar SwingSet2

clean:
	rm -rf *.class
