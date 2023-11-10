// Define an interface for modifying data

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.sql.*;

class UpdataJFrame extends JDialog implements ActionListener {

    // Fill in the information space
    private String sex = null;
    JTextField[] jt = new JTextField[5];
    JButton[] jb = new JButton[2];
    // radio button
    JRadioButton[] jradio = new JRadioButton[2];
    // Combination radio button
    ButtonGroup group;
    // Used to set box layout BoxLayout
    Box[] mybox = new Box[4];
    JPanel jp, jp1;
    // Several commonly used variable types in SQL
    Connection ct = null;
    PreparedStatement ps = null;

    public UpdataJFrame(Frame Father, boolean Model, String name) {
        // Adopting Mode Dialogs
        super(Father, Model);
        for (int i = 0; i < 5; i++)
            jt[i] = new JTextField(10);
        // Set to read only
        jt[0].setEditable(false);
        // Several buttons below
        jb[0] = new JButton("YES");
        jb[0].addActionListener(this);
        jb[1] = new JButton("NO");
        jb[1].addActionListener(this);
        jradio[0] = new JRadioButton("MAN");
        jradio[0].addActionListener(this);
        jradio[1] = new JRadioButton("WOMAN");
        jradio[1].addActionListener(this);
        // Set Horizontal
        mybox[0] = Box.createVerticalBox();
        mybox[0].add(Box.createVerticalStrut(15));
        mybox[0].add(new JLabel("STUDENT ID："));
        mybox[0].add(Box.createVerticalStrut(10));
        mybox[0].add(new JLabel("NAME："));
        mybox[0].add(Box.createVerticalStrut(20));
        mybox[0].add(new JLabel("SEX："));
        mybox[0].add(Box.createVerticalStrut(25));
        mybox[0].add(new JLabel("AGE："));
        mybox[0].add(Box.createVerticalStrut(10));
        mybox[0].add(new JLabel("ADDRESS："));
        mybox[0].add(Box.createVerticalStrut(10));
        mybox[0].add(new JLabel("DEPARTMENT："));
        mybox[0].add(Box.createVerticalStrut(10));
        // Set the level on the other side
        mybox[1] = Box.createVerticalBox();
        mybox[1].add(Box.createVerticalStrut(18));
        mybox[1].add(jt[0]);
        mybox[1].add(Box.createVerticalStrut(8));
        mybox[1].add(jt[1]);
        mybox[1].add(Box.createVerticalStrut(8));
        jp = new JPanel();
        // Single choice group
        group = new ButtonGroup();
        group.add(jradio[0]);
        group.add(jradio[1]);
        jp.add(jradio[0]);
        jp.add(jradio[1]);
        mybox[1].add(jp);

        /*
         * group =new ButtonGroup(); group.add(jradio[0]); group.add(jradio[1]);
         * mybox[1].add(group); What needs to be changed here is to change it to combo, or simply change it to the same text box
         */
        mybox[1].add(Box.createVerticalStrut(5));
        mybox[1].add(jt[2]);
        mybox[1].add(Box.createVerticalStrut(8));
        mybox[1].add(jt[3]);
        mybox[1].add(Box.createVerticalStrut(8));
        mybox[1].add(jt[4]);
        mybox[1].add(Box.createVerticalStrut(8));

        // Button section
        jp1 = new JPanel();
        jp1.add(jb[0], BorderLayout.EAST);
        jp1.add(jb[1], BorderLayout.WEST);
        mybox[2] = Box.createHorizontalBox();
        mybox[2].add(mybox[0]);
        mybox[2].add(Box.createHorizontalStrut(10));
        mybox[2].add(mybox[1]);
        Connect(name);
        this.add(mybox[2], BorderLayout.NORTH);
        this.add(jp1, BorderLayout.SOUTH);
        init();
    }

    // Display section
    public void init() {
        this.setTitle("Modify student information");
        this.setBackground(Color.magenta);
        this.setBounds(800, 600, 600, 600);
        this.setVisible(true);
        // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void Connect(String name) {
        ResultSet rs = null;
        // Drive loading
        try {
            String driver = "jdbc:derby://localhost:1527/student;create=true;";
            ct = DriverManager.getConnection(driver);
            String sql = new String("Select * from student where stuId=?");
            ps = ct.prepareStatement(sql);
            ps.setString(1, name);
            rs = ps.executeQuery();
            while (rs.next()) {
                int cnt = 1;
                for (int i = 1; i <= 6; i++) {
                    if (4 == i) {
                        // Convert integers to Strings
                        jt[i - cnt].setText(String.valueOf(rs.getInt(i)));
                    } else {
                        String st = null;
                        try {
                            st = new String((rs.getString(i)).getBytes("gbk"),
                                    "gb2312");
                        } catch (UnsupportedEncodingException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        if (i == 3) {
                            if (st.equals("MAN")) {
                                jradio[0].setSelected(true);
                                this.setSex("MAN");
                            } else if (st.equals("WOMAN")) {
                                jradio[1].setSelected(true);
                                this.setSex("WOMAN");
                            } else
                                JOptionPane.showMessageDialog(this, "There are garbled codes at the gender position");
                            cnt++;
                        } else
                            jt[i - cnt].setText(st);
                    }
                }

            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
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

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        // Write data to the database
        if (e.getSource() == jb[0]) {
            // Process the written data
            try {
                // Drive loading
                String driver = "jdbc:derby://localhost:1527/student;create=true;";
                ct = DriverManager.getConnection(driver);
                ps = ct.prepareStatement("Update student Set Stuname=?,StuSex=?,StuAge=?,StuJg=?,StuDept=? where StuId=?");
                // Come back and modify
                // NAME
                ps.setString(1, jt[1].getText().toString().trim());
                // SEX
                ps.setString(2, this.getSex().trim());
                // AGE
                String tem = jt[2].getText().toString().trim();
                int value = Integer.valueOf(tem).intValue();
                ps.setInt(3, value);
                // native place
                ps.setString(4, jt[3].getText().toString().trim());
                // DEPARTMENT
                ps.setString(5, jt[4].getText().toString().trim());
                // STUDENT ID
                ps.setString(6, jt[0].getText().toString().trim());
                // UPDATE
                int i = ps.executeUpdate();
                if (1 == i)
                    JOptionPane.showMessageDialog(this, "update success！");
                else
                    JOptionPane.showMessageDialog(this, "Add failed！");

            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } finally {
                // Close some calling functions of the database
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
            this.dispose();
        } else if (e.getSource() == jb[0])
            // Release the window to exit the layout
            this.dispose();
        else if (jradio[0].isSelected()) {
            // Change name to male
            this.setSex("MAN");
        } else if (jradio[1].isSelected()) {
            // Change the name to female
            this.setSex("WOMAN");
        }
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
 