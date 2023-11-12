package src;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class StudentJDBC {
    private static StudentJDBC instance = new StudentJDBC();

    private StudentJDBC() {

    }

    public static StudentJDBC getInstance() {
        return instance;
    }

    public int delete(String name) {
        Connection ct = null;
        PreparedStatement ps = null;
        try {
            ct = JDBCManager.getConn();
            String sql = new String("DELETE FROM student WHERE Stuname=?");
            ps = ct.prepareStatement(sql);
            ps.setString(1, name.trim());
            int i = ps.executeUpdate();
            return i;
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (ct != null) {
                    ct.close();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return 0;
    }

    public Vector select(String name) {
        Connection ct = null;
        PreparedStatement ps = null;
        String sql;
        ResultSet rs = null;
        Vector rowdata = new Vector();
        try {
            if (name != null && !name.equals("")) {
                sql = new String("SELECT * FROM student WHERE Stuname=\'" + name + "\'");
            } else {
                sql = new String("SELECT * FROM student");
            }
            ct = JDBCManager.getConn();
            ps = ct.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Vector row = new Vector();
                for (int i = 1; i <= 6; i++) {
                    if (4 == i)
                        row.add(rs.getInt(i));
                    else {
                        String st = new String(
                                (rs.getString(i)).getBytes("gbk"), "gb2312");
                        row.add(st);
                    }
                }
                rowdata.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowdata;
    }

    public int insert(String stuId, String stuName, String stuSex, int stuAge, String stuJg, String stuDept) {
        Connection ct = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ct = JDBCManager.getConn();
            ps = ct.prepareStatement("INSERT INTO student VALUES(?,?,?,?,?,?)");
            ps.setString(1, stuId);
            ps.setString(2, stuName);
            ps.setString(3, stuSex);
            ps.setInt(4, stuAge);
            ps.setString(5, stuJg);
            ps.setString(6, stuDept);

            int i = ps.executeUpdate();
            return i;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int update(String stuId, String stuName, String stuSex, int stuAge, String stuJg, String stuDept) {
        Connection ct = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ct = JDBCManager.getConn();
            ps = ct.prepareStatement("UPDATE student SET Stuname=?, StuSex=?, StuAge=?, StuJg=?, StuDept=? WHERE StuId=?");
            // Name
            ps.setString(1, stuName);
            // Gender
            ps.setString(2, stuSex);
            // Age
            ps.setInt(3, stuAge);
            // Hometown
            ps.setString(4, stuJg);
            // Department
            ps.setString(5, stuDept);
            // Student ID
            ps.setString(6, stuId);
            // Update
            int i = ps.executeUpdate();
            return i;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
