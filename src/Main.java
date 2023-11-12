package src;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static final Font DEFAULT_FOUNT_18 = new Font("黑体", Font.BOLD, 18);
    public static final Image WINDOWS_ICON = new ImageIcon("image\\icon.png").getImage();

    static {
        try {
            // Load the driver
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            Connection conn = JDBCManager.getConn();
            Statement stmt = conn.createStatement();

            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "student".toUpperCase(), null);

            if (!resultSet.next()) {
                String sql =
                        "create table student(" +
                                "StuId int primary key, " +
                                "Stuname varchar(100), " +
                                "StuSex varchar(100), " +
                                "StuAge varchar(100)," +
                                "StuJg varchar(100)," +
                                "StuDept varchar(100)" +
                                ")";
                stmt.executeUpdate(sql);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        // Create a panel object
//        MyJFrame mf = new MyJFrame();
//        mf.show();
//          AdminLogin adminLogin = new AdminLogin();
//          adminLogin.show();
        MyJFrame mf = new MyJFrame();
        mf.show();
    }
}
