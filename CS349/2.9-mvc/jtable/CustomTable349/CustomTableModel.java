
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;

/*
 *  Show how to use an AbstractTableModel as part of your
 *  own application's model.  There are a number of possibilities:
 *  -- Make the application's model extend AbstractTableModel
 *  -- Provide an inner class to JTable (the one shown here)
 *  -- Implement the TableModel interface yourself (but why, given
 *     AbstractTableModel?)
 */ 

public class CustomTableModel {
	private ArrayList<Person> persons = new ArrayList<Person>();
	private boolean DEBUG = true;

	public CustomTableModel() {
		// Initialize data
		this.persons.add(new Person("Kathy", "Smith", "Snowboarding", 5, false));
	    this.persons.add(new Person("John", "Doe","Rowing", 3, true));
	    this.persons.add(new Person("Sue", "Black", "Knitting", 2, false));
	    this.persons.add(new Person("Jane", "White", "Speed reading", 20, true));
	    this.persons.add(new Person("Joe", "Brown", "Pool", 10, false));
	    this.persons.add(new Person("Kathy", "Smith", "Snowboarding", 5, false));
	    this.persons.add(new Person("John", "Doe", "Rowing", 3, true));
	    this.persons.add(new Person("Sue", "Black", "Knitting", 2, false));
	    this.persons.add(new Person("Jane", "White", "Speed reading", 20, true));
	    this.persons.add(new Person("Joe", "Brown", "Pool", 10, false));
	}

	/*
	 * This doesn't do anything; just illustrates that CustomTableModel interface now
	 * conforms much more closely to a pure MVC model, unpolluted by the 
	 * AbstractTableModel stuff that's just there for one widget.
	 */
	public void addPerson(Person p) {
		this.persons.add(p);
		atmInstance.fireTableRowsInserted(this.persons.size(), this.persons.size());
	}

	/*
	 * Make an instance of an abstract table model to use with JTables.
	 */
	private AbstractTableModel atmInstance = new AbstractTableModel(){
	   	// our data, note we can call these whatever we want
        private String[] myDataColumnNames = {"First Name",
                                        "Last Name",
                                        "Sport",
                                        "# of Years",
                                        "Vegetarian"};

        // need to define these three methods:
        
        public int getColumnCount() {
            return 5;
        }

        public int getRowCount() {
            return persons.size();
        }

        public Object getValueAt(int row, int col) {
        	switch(col) {
        		case 0: return persons.get(row).firstName; 
        		case 1: return persons.get(row).lastName; 
        		case 2: return persons.get(row).sport;
        		case 3: return persons.get(row).years;
        		case 4: return persons.get(row).vegitarian;
        	}
        	return null;	// for the compiler :(
        }
        
        // define this if you don't want default 'A', 'B', ... names
        public String getColumnName(int col) {
            return myDataColumnNames[col];
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass(); // reflection!
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell appendress is constant,
            //no matter where the cell appears onscreen.
        	//(because it's the appendress in the MODEL, not the VIEW)
            if (col < 2) {
                return false;
            } else {
                return true;
            }
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col) {
            if (DEBUG) {
                System.out.println("Setting value at " + row + "," + col
                                   + " to " + value
                                   + " (an instance of "
                                   + value.getClass() + ")");
            }

            switch(col) {
            	// columns 0, 1 aren't editable
            	case 2: persons.get(row).sport = (String)value; break;
            	case 3: persons.get(row).years = (Integer)value; break;
            	case 4: persons.get(row).vegitarian = ((Boolean)value).booleanValue(); break;

            }
            fireTableCellUpdated(row, col);

            if (DEBUG) {
                System.out.println("New value of data:");
                printDebugData();
            }
        }
	};

	public TableModel getTableModel() {
		return atmInstance;
	}

    private void printDebugData() {
    	for(Person p : this.persons) {
    		System.out.println(p);
    	}
        System.out.println("--------------------------");
    }

}

class Person {
	String	firstName;
	String lastName;
	String sport;
	int	years;
	boolean vegitarian;

	public Person(String firstName, String lastName, String sport,
		int years, boolean vegitarian) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.sport = sport;
		this.years = years;
		this.vegitarian = vegitarian;
	}

	public String toString() {
		return "[" + this.firstName + ", " + this.lastName + ", " +
		this.sport + ", " + this.years + ", " + this.vegitarian + "]";
	}
}