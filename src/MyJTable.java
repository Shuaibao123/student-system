package src;

import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

// Define a custom table class
class MyJTable extends AbstractTableModel {

    // SQL variables
    Connection ct = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    Vector rowdata, colName;

    // Constructor without parameters for initialization
    MyJTable() {
        this.func("", false);
    }

    // Parameterized constructor for querying
    MyJTable(String name) {
        this.func(name, true);
    }

    public void func(String name, boolean tag) {
        // Load the JDBC-ODBC driver

        /*
         * Set the properties of the table
         */
        colName = new Vector();
        rowdata = StudentJDBC.getInstance().select(name);
        String[] ss = {"Student ID", "Name", "Gender", "Age", "Home Address", "Department"};
        for (int i = 0; i < 6; i++)
            colName.add(ss[i]);
    }

    // Return the number of rows
    @Override
    public int getRowCount() {
        return this.rowdata.size();
    }

    // Return the number of columns
    @Override
    public int getColumnCount() {
        return this.colName.size();
    }

    // Return the content of the table
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return ((Vector) this.rowdata.get(rowIndex)).get(columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return (String) this.colName.get(column);
    }
}
