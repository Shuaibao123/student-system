// Delete Interface Class

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

class DeleteStudent extends JDialog implements ActionListener {

    // Set to two buttons: confirm and cancel
    JButton[] button = new JButton[2];
    JPanel jp;
    JTable jt;
    JScrollPane jsp = null;
    // Set as a deleted form
    MyJTable mytable;
    // Several commonly used variable types in SQL

    Connection ct = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    private String name;

    public DeleteStudent(Frame ower, String name, boolean Model) {

        super(ower, Model);
        this.setName(name);
        jp = new JPanel();
        button[0] = new JButton("YES");
        button[0].addActionListener(this);
        button[1] = new JButton("NO");
        button[1].addActionListener(this);
        jp.add(button[0]);
        jp.add(button[1]);

        // Set up an interface for my list
        mytable = new MyJTable(name);
        jt = new JTable(mytable);
        JScrollPane jsp = new JScrollPane(jt);
        this.add(jp, BorderLayout.SOUTH);
        this.add(jsp, BorderLayout.NORTH);
        this.setTitle("Delete interface");
        this.setSize(800, 500);
        this.setResizable(false);
        this.setVisible(true);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == button[0]) {

            try {
                // Load jdbc_ Odbc driver
                String driver = "jdbc:derby://localhost:1527/student;create=true;";
                ct = DriverManager.getConnection(driver);
                String sql = new String("delete  from student where Stuname=?");
                ps = ct.prepareStatement(sql);
                ps.setString(1, this.getName().trim());

                int i = ps.executeUpdate();
                if (1 == i)
                    JOptionPane.showMessageDialog(this, "Successfully deleted");
                else
                    JOptionPane.showMessageDialog(this, "Deletion failed！");

            } catch ( SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } finally {
                try {
                    if (ps != null)
                        ps.close();
                    if (ct != null)
                        ct.close();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        }

        this.dispose();
    }
}
