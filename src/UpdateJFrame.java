package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.sql.*;

class UpdateJFrame extends JDialog implements ActionListener {

    // Fields for entering information
    private String sex = null;
    JTextField[] jt = new JTextField[5];
    JButton[] jb = new JButton[2];
    // Radio buttons
    JRadioButton[] jradio = new JRadioButton[2];
    // Radio button group
    ButtonGroup group;
    // Boxes for layout using BoxLayout
    Box[] mybox = new Box[4];
    JPanel jp, jp1;
    // SQL variables
    Connection ct = null;
    PreparedStatement ps = null;
    private MyJFrame father;

    public UpdateJFrame(Frame father, boolean modal, String name) {
        this.father = (MyJFrame) father;

        // Using a modal dialog
        // super(father, modal);
        for (int i = 0; i < 5; i++)
            jt[i] = new JTextField(10);
        // Set the first field as read-only
        jt[0].setEditable(false);
        // Buttons at the bottom
        jb[0] = new JButton("Confirm");
        jb[0].addActionListener(this);
        jb[1] = new JButton("Cancel");
        jb[1].addActionListener(this);
        jradio[0] = new JRadioButton("Male");
        jradio[0].addActionListener(this);
        jradio[1] = new JRadioButton("Female");
        jradio[1].addActionListener(this);
        // Create vertical boxes
        mybox[0] = Box.createVerticalBox();
        mybox[0].add(Box.createVerticalStrut(15));
        mybox[0].add(new JLabel("Student ID:"));
        mybox[0].add(Box.createVerticalStrut(10));
        mybox[0].add(new JLabel("Name:"));
        mybox[0].add(Box.createVerticalStrut(20));
        mybox[0].add(new JLabel("Gender:"));
        mybox[0].add(Box.createVerticalStrut(25));
        mybox[0].add(new JLabel("Age:"));
        mybox[0].add(Box.createVerticalStrut(10));
        mybox[0].add(new JLabel("Home Address:"));
        mybox[0].add(Box.createVerticalStrut(10));
        mybox[0].add(new JLabel("Department:"));
        mybox[0].add(Box.createVerticalStrut(10));
        // Create another vertical box for input fields
        mybox[1] = Box.createVerticalBox();
        mybox[1].add(Box.createVerticalStrut(18));
        mybox[1].add(jt[0]);
        mybox[1].add(Box.createVerticalStrut(8));
        mybox[1].add(jt[1]);
        mybox[1].add(Box.createVerticalStrut(8));
        jp = new JPanel();
        // Radio button group
        group = new ButtonGroup();
        group.add(jradio[0]);
        group.add(jradio[1]);
        jp.add(jradio[0]);
        jp.add(jradio[1]);
        mybox[1].add(jp);

        mybox[1].add(Box.createVerticalStrut(5));
        mybox[1].add(jt[2]);
        mybox[1].add(Box.createVerticalStrut(8));
        mybox[1].add(jt[3]);
        mybox[1].add(Box.createVerticalStrut(8));
        mybox[1].add(jt[4]);
        mybox[1].add(Box.createVerticalStrut(8));

        // Button panel
        jp1 = new JPanel();
        jp1.add(jb[0], BorderLayout.EAST);
        jp1.add(jb[1], BorderLayout.WEST);
        mybox[2] = Box.createHorizontalBox();
        mybox[2].add(Box.createHorizontalStrut(25));
        mybox[2].add(mybox[0]);
        mybox[2].add(Box.createHorizontalStrut(10));
        mybox[2].add(mybox[1]);
        mybox[2].add(Box.createHorizontalStrut(25));
        Connect(name);
        this.add(mybox[2], BorderLayout.NORTH);
        this.add(jp1, BorderLayout.SOUTH);
        init();
        this.setLocationRelativeTo(null);
        this.setSize(400, 280);
    }

    // Initialize the dialog
    public void init() {
        this.setTitle("Update Student Information");
        this.setBackground(Color.MAGENTA);
        this.setBounds(800, 600, 400, 280);
        this.setVisible(true);
        // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Connect to the database and fill in the form fields
    public void Connect(String name) {
        ResultSet rs = null;
        try {

            ct = JDBCManager.getConn();
            String sql = new String("Select * from student where stuId=?");
            ps = ct.prepareStatement(sql);
            ps.setString(1, name);
            rs = ps.executeQuery();
            while (rs.next()) {
                int cnt = 1;
                for (int i = 1; i <= 6; i++) {
                    if (4 == i) {
                        // Convert integer to string
                        jt[i - cnt].setText(String.valueOf(rs.getInt(i)));
                    } else {
                        String st = null;
                        try {
                            st = new String((rs.getString(i)).getBytes("gbk"),
                                    "gb2312");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        if (i == 3) {
                            if (st.equals("Male")) {
                                jradio[0].setSelected(true);
                                this.setSex("Male");
                            } else if (st.equals("Female")) {
                                jradio[1].setSelected(true);
                                this.setSex("Female");
                            } else
                                JOptionPane.showMessageDialog(this, "Gender appears as garbled characters");
                            cnt++;
                        } else
                            jt[i - cnt].setText(st);
                    }
                }

            }

        } catch (SQLException e) {
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
                e.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle button clicks
        if (e.getSource() == jb[0]) {
            // Update the data in the database
            int i = StudentJDBC.getInstance().update(
                    jt[0].getText().toString().trim(),
                    jt[1].getText().toString().trim(),
                    this.getSex().trim(),
                    Integer.valueOf(jt[2].getText().toString().trim()).intValue(),
                    jt[3].getText().toString().trim(),
                    jt[4].getText().toString().trim()

            );
            if (1 == i)
                JOptionPane.showMessageDialog(this, "Update Successful!");
            else
                JOptionPane.showMessageDialog(this, "Update Failed!");
            father.refresh();
            this.dispose();
        } else if (e.getSource() == jb[1])
            // Release and close the window
            this.dispose();
        else if (jradio[0].isSelected()) {
            // Set gender to Male
            this.setSex("Male");
        } else if (jradio[1].isSelected()) {
            // Set gender to Female
            this.setSex("Female");
        }
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
