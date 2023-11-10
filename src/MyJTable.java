import javax.swing.table.AbstractTableModel;
import java.sql.*;
import java.util.Vector;

// Define your own table class
class MyJTable extends AbstractTableModel {

    // Several variables in SQL
    Connection ct = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    Vector rowdata, colName;

    // Constructor without parameters, used for initialization
    MyJTable() {
        this.func("", false);
    }

    // Constructor with parameters for querying
    MyJTable(String name) {
        this.func(name, true);
    };

    public void func(String name, boolean tag) {


        String sql;
        try {
            // Load jdbc_ Odbc driver
            String driver = "jdbc:derby://localhost:1527/student;create=true;";
            try {
                ct = DriverManager.getConnection(driver);
            }catch(Exception e) {
                e.printStackTrace();
            }

            if (!tag) {
                sql = new String("select * from student");
                ps = ct.prepareStatement(sql);
            } else {
                sql = new String("Select * from student where Stuname=?");
                ps = ct.prepareStatement(sql);
                ps.setString(1, name);
            }
            rs = ps.executeQuery();
            /*
             * Set the properties of the form
             */
            colName = new Vector();
            rowdata = new Vector();
            String[] ss = { "STUDENT ID", "NAME", "SEX", "AGE", "ADDRESS", "DEPARTMENT" };
            for (int i = 0; i < 6; i++)
                colName.add(ss[i]);
            while (rs.next()) {
                Vector hang = new Vector();
                for (int i = 1; i <= 6; i++) {
                    if (4 == i)
                        hang.add(rs.getInt(i));
                    else {
                        String st = new String(
                                (rs.getString(i)).getBytes("gbk"), "gb2312");
                        hang.add(st);
                    }
                }
                rowdata.add(hang);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            // Close some windows
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (ct != null)
                    ct.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    // Returns its number of rows
    @Override
    public int getRowCount() {
        // TODO Auto-generated method stub
        return this.rowdata.size();
    }

    // Returns its number of columns
    @Override
    public int getColumnCount() {
        // TODO Auto-generated method stub
        return this.colName.size();
    }

    // Return the content of this form
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        // TODO Auto-generated method stub
        return ((Vector) this.rowdata.get(rowIndex)).get(columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        // TODO Auto-generated method stub
        return (String) this.colName.get(column);
    }
}
 